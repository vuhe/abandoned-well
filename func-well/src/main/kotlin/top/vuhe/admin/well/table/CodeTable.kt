package top.vuhe.admin.well.table

import org.ktorm.schema.text
import org.ktorm.schema.varchar
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.well.domina.RegionCode

object CodeTable : IdTable<RegionCode>("region_code") {
    override val id = varchar("id").primaryKey().bindTo { it.id }
    val code = varchar("code").bindTo { it.code }
    val remark = text("remark").bindTo { it.remark }
}
