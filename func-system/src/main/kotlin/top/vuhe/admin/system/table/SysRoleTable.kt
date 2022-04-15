package top.vuhe.admin.system.table

import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.system.domain.SysRole

object SysRoleTable : IdTable<SysRole>("sys_role") {
    override val id = varchar("role_id").primaryKey().bindTo { it.roleId }
    val roleName = varchar("role_name").bindTo { it.roleName }
    val roleCode = varchar("role_code").bindTo { it.roleCode }
    val enable = boolean("enable").bindTo { it.enable }
    val details = varchar("details").bindTo { it.details }
    val sort = int("sort").bindTo { it.sort }
}
