package top.vuhe.admin.system.param

import org.ktorm.dsl.and
import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.table.SysUserTable

class SysUserParam : PageParam() {
    var realName: String = ""
    var username: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        val list = buildList {
            if (realName.isNotBlank()) add(SysUserTable.realName like "%${realName}%")
            if (username.isNotBlank()) add(SysUserTable.username like "%${username}%")
        }
        return if (list.isEmpty()) null
        else list.reduce { a, b -> a and b }
    }
}
