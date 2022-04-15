package top.vuhe.admin.well.table

import org.ktorm.schema.int
import org.ktorm.schema.varchar
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.well.domina.WellRegion

object RegionTable : IdTable<WellRegion>("well_region") {
    override val id = varchar("id").primaryKey().bindTo { it.id }
    val city = varchar("city").bindTo { it.city }
    val county = varchar("county").bindTo { it.county }
    val districtCode = varchar("district_code").bindTo { it.districtCode }
    val regionCodeId = varchar("region_code").bindTo { it.regionCodeId }
    val sort = int("sort").bindTo { it.sort }
    val next = int("count").bindTo { it.next }
}
