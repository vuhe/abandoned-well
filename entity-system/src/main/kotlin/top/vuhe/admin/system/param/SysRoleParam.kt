package top.vuhe.admin.system.param

import org.ktorm.dsl.and
import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.table.SysRoleTable

class SysRoleParam : PageParam() {
    var roleName: String = ""
    var roleCode: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        val list = buildList {
            if (roleCode.isNotBlank()) add(SysRoleTable.roleCode like "%${roleCode}%")
            if (roleName.isNotBlank()) add(SysRoleTable.roleName like "%${roleName}%")
        }
        return if (list.isEmpty()) null
        else list.reduce { a, b -> a and b }
    }
}
