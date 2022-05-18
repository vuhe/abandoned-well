package top.vuhe.domain.role

import org.ktorm.dsl.and
import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.web.request.PageParam

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
