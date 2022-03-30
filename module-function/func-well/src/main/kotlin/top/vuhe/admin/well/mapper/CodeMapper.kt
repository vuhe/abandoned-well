package top.vuhe.admin.well.mapper

import org.ktorm.schema.text
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.well.domina.RegionCode

/**
 * 地质分区代码接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class CodeMapper: CurdMapper<RegionCode>("region_code") {
    override val id = varchar("id").primaryKey().bind(RegionCode::id)
    private val code = varchar("code").bind(RegionCode::code)
    private val remark = text("remark").bind(RegionCode::remark)
}
