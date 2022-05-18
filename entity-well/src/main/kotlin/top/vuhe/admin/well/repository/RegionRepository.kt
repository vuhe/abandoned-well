package top.vuhe.admin.well.repository

import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.table.RegionTable

/**
 * 井区域接口
 *
 * @author vuhe
 */
@Repository
class RegionRepository : CurdRepository<RegionTable, WellRegion>() {
    override val table get() = RegionTable
    override var WellRegion.entityId: String by WellRegion::id
}
