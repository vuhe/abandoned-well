package top.vuhe.admin.system.table

import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.system.domain.SysPower

object SysPowerTable : IdTable<SysPower>("sys_power") {
    override val id = varchar("power_id").primaryKey().bindTo { it.powerId }
    val powerName = varchar("power_name").bindTo { it.powerName }
    val powerType = varchar("power_type").bindTo { it.powerType }
    val powerCode = varchar("power_code").bindTo { it.powerCode }
    val powerUrl = varchar("power_url").bindTo { it.powerUrl }
    val openType = varchar("open_type").bindTo { it.openType }
    val parentId = varchar("parent_id").bindTo { it.parentId }
    val icon = varchar("icon").bindTo { it.icon }
    val sort = int("sort").bindTo { it.sort }
    val enable = boolean("enable").bindTo { it.enable }
}
