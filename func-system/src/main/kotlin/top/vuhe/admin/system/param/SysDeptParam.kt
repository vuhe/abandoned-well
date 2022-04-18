package top.vuhe.admin.system.param

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.table.SysDeptTable

class SysDeptParam : PageParam() {
    var deptName: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (deptName.isNotBlank()) {
            SysDeptTable.deptName like "%${deptName}%"
        } else null
    }
}
