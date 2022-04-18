package top.vuhe.admin.system.param

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.table.SysPowerTable

class SysPowerParam : PageParam() {
    var powerName: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (powerName.isNotBlank()) {
            SysPowerTable.powerName like "%${powerName}%"
        } else null
    }
}
