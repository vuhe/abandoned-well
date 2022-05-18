package top.vuhe.admin.well.repository

import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.well.domina.RegionCode
import top.vuhe.admin.well.table.CodeTable

/**
 * 地质分区代码接口
 *
 * @author vuhe
 */
@Repository
class CodeRepository : CurdRepository<CodeTable, RegionCode>() {
    override val table get() = CodeTable
    override var RegionCode.entityId: String by RegionCode::id
}
