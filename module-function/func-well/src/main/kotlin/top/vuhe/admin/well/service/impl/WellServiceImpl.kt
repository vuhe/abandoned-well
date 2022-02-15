package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.well.domina.ExportWell
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.mapper.CodeMapper
import top.vuhe.admin.well.mapper.RegionMapper
import top.vuhe.admin.well.mapper.WellMapper
import top.vuhe.admin.well.service.IWellService

/**
 * 井信息服务类接口实现
 *
 * @author vuhe
 */
@Service
class WellServiceImpl(
    private val infoMapper: WellMapper,
    private val regionMapper: RegionMapper,
    private val regionCodeMapper: CodeMapper
) : CurdService<WellInfo>(infoMapper), IWellService {
    private val emptyParam = WellInfo()

    override fun exportList(): List<ExportWell> {
        return list(emptyParam).mapNotNull {
            val region = regionMapper.selectById(it.regionId)
            if (region != null) ExportWell(it, region)
            else null
        }
    }

    /**
     * 添加实体，使用自定义的 id 生成策略
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: WellInfo): Boolean {
        val region = regionMapper.selectById(entity.regionId)
            ?: throw BusinessException("地区错误，请刷新后重试")

        // 地区顺序码 + 1
        region.next = region.next?.plus(1)

        val code = regionCodeMapper.selectById(region.regionCodeId)
            ?: throw BusinessException("水文代码错误，请刷新后重试")

        // 生成井 id
        val wellId = "%s%04d%sW".format(region.districtCode, region.next, code.code)
        entity.id = wellId

        // 插入成功后，将顺序码更改写入数据库
        return if (infoMapper.insert(entity) > 0) {
            regionMapper.update(region)
            true
        } else false
    }
}
