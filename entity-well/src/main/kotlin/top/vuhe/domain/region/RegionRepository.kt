package top.vuhe.domain.region

import org.springframework.stereotype.Repository
import top.vuhe.database.repository.CurdRepository

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
