package top.vuhe.domain.well

import org.springframework.stereotype.Repository
import top.vuhe.database.repository.CurdRepository

/**
 * 井信息接口
 *
 * @author vuhe
 */
@Repository
class WellRepository : CurdRepository<WellTable, WellInfo>() {
    override val table get() = WellTable
    override var WellInfo.entityId: String by WellInfo::id
}
