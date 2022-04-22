package top.vuhe.admin.well.service

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.ExportWell
import top.vuhe.admin.well.domina.WellInfo
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
    private val reporting: ReportingService
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

    override fun add(entity: WellInfo): Boolean {
        val result = reporting.serialAdd(entity)
        return result.runCatching { get() }.getOrElse { false }
    }
}
