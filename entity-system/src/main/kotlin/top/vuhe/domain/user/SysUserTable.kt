package top.vuhe.domain.user

import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.varchar
import top.vuhe.database.table.IdTable

object SysUserTable : IdTable<SysUser>("sys_user") {
    override val id = varchar("user_id").primaryKey().bindTo { it.userId }
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val realName = varchar("real_name").bindTo { it.realName }
    val email = varchar("email").bindTo { it.email }
    val sex = varchar("sex").bindTo { it.sex }
    val phone = varchar("phone").bindTo { it.phone }
    val deptId = varchar("dept_id").bindTo { it.deptId }
    val unlocked = boolean("status").bindTo { it.unlocked }
    val enable = boolean("enable").bindTo { it.enable }
    val admin = boolean("admin").bindTo { it.admin }
    val lastTime = datetime("last_time").bindTo { it.lastTime }
    val createTime = datetime("create_time").bindTo { it.createTime }
    val remark = varchar("remark").bindTo { it.remark }
}
