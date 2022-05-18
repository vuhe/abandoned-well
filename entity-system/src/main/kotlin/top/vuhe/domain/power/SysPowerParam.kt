package top.vuhe.domain.power

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.web.request.PageParam

class SysPowerParam : PageParam() {
    var powerName: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (powerName.isNotBlank()) {
            SysPowerTable.powerName like "%${powerName}%"
        } else null
    }
}
