package top.vuhe.admin.well.repository

import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.table.WellTable

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
