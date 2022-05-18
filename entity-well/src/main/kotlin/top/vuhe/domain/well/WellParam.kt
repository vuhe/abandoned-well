package top.vuhe.domain.well

import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.web.request.PageParam

class WellParam : PageParam() {
    var name: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        return if (name.isNotBlank()) {
            WellTable.name like "%${name}%"
        } else null
    }
}
