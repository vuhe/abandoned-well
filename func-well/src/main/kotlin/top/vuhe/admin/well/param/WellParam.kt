package top.vuhe.admin.well.param

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.well.table.WellTable

class WellParam : PageParam() {
    var name: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (name.isNotBlank()) {
            WellTable.name like "%${name}%"
        } else null
    }
}
