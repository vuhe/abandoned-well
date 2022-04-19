package top.vuhe.admin.well.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.exception.businessNotNull
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.ExportWell
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.repository.CodeRepository
import top.vuhe.admin.well.repository.RegionRepository
import top.vuhe.admin.well.repository.WellRepository

/**
 * 井信息服务类接口实现
 *
 * @author vuhe
 */
@Service
class WellService(
    override val repository: WellRepository,
    private val regions: RegionRepository,
    private val regionCodes: CodeRepository
) : CurdService<WellInfo>() {

    /**
     * 导出数据列表
     */
    fun exportList(): List<ExportWell> {
        return list().mapNotNull {
            val region = regions.selectById(it.regionId)
            if (region != null) ExportWell(it, region)
            else null
        }
    }

    /**
     * 添加实体，使用自定义的 id 生成策略
     *
     * 此方法使用了其他表的字段进行主键生成，这可能会产生不可重复读，
     * 为了保证上报的正确性，此方法指定使用「可重复读」
     * TODO need modify
     */
    @Transactional(rollbackFor = [Exception::class], isolation = Isolation.REPEATABLE_READ)
    override fun add(entity: WellInfo): Boolean {
        val region = businessNotNull(
            regions.selectById(entity.regionId)
        ) { "地区错误，请刷新后重试" }

        // 地区顺序码 + 1
        region.next = region.next + 1

        val code = businessNotNull(
            regionCodes.selectById(region.regionCodeId)
        ) { "水文代码错误，请刷新后重试" }

        // 生成井 id
        entity.id = "%s%04d%sW".format(region.districtCode, region.next, code.code)

        // 插入成功后，将顺序码更改写入数据库
        return if (repository.insert(entity) > 0) {
            regions.update(region)
            true
        } else false
    }
}
