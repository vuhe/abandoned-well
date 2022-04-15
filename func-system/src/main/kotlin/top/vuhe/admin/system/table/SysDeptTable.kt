package top.vuhe.admin.system.table

import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.system.domain.SysDept

object SysDeptTable : IdTable<SysDept>("sys_dept") {
    override val id = varchar("dept_id").primaryKey().bindTo { it.deptId }
    val deptName = varchar("dept_name").bindTo { it.deptName }
    val address = varchar("address").bindTo { it.address }
    val parentId = varchar("parent_id").bindTo { it.parentId }
    val leader = varchar("leader").bindTo { it.leader }
    val phone = varchar("phone").bindTo { it.phone }
    val email = varchar("email").bindTo { it.email }
    val enable = boolean("status").bindTo { it.enable }
    val sort = int("sort").bindTo { it.sort }
}
