package top.vuhe.domain.code

import org.springframework.stereotype.Repository
import top.vuhe.database.repository.CurdRepository

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
