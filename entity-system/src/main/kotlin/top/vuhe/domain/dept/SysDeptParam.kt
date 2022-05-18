package top.vuhe.domain.dept

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.web.request.PageParam

class SysDeptParam : PageParam() {
    var deptName: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (deptName.isNotBlank()) {
            SysDeptTable.deptName like "%${deptName}%"
        } else null
    }
}
