package top.vuhe.domain.region

import org.ktorm.dsl.and
import org.ktorm.dsl.like
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.web.request.PageParam

class RegionParam : PageParam() {
    var city: String = ""
    var county: String = ""

    override fun query(): ColumnDeclaring<Boolean>? {
        val list = buildList {
            if (city.isNotBlank()) add(RegionTable.city like "%${city}%")
            if (county.isNotBlank()) add(RegionTable.county like "%${county}%")
        }
        return if (list.isEmpty()) null
        else list.reduce { a, b -> a and b }
    }
}
