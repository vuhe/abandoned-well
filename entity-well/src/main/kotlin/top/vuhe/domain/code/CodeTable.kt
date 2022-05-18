package top.vuhe.domain.code

import org.ktorm.schema.text
import org.ktorm.schema.varchar
import top.vuhe.database.table.IdTable

object CodeTable : IdTable<RegionCode>("region_code") {
    override val id = varchar("id").primaryKey().bindTo { it.id }
    val code = varchar("code").bindTo { it.code }
    val name = varchar("name").bindTo { it.name }
    val remark = text("remark").bindTo { it.remark }
}
