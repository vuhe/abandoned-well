package top.vuhe.admin.well.table

import org.ktorm.schema.*
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.domina.WellStatus
import top.vuhe.admin.well.domina.WellType

object WellTable : IdTable<WellInfo>("well_info") {
    override val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val originCode = varchar("origin_code").bindTo { it.originCode }
    val wellType = enum<WellType>("well_type").bindTo { it.wellType }
    val regionId = varchar("region_id").bindTo { it.regionId }
    val address = varchar("address").bindTo { it.address }
    val street = varchar("street").bindTo { it.street }
    val company = varchar("company").bindTo { it.company }
    val digTime = date("dig_time").bindTo { it.digTime }
    val contacts = varchar("contacts").bindTo { it.contacts }
    val phone = varchar("phone").bindTo { it.phone }
    val abandonRemark = text("abandon_remark").bindTo { it.abandonRemark }
    val abandonTime = date("abandon_time").bindTo { it.abandonTime }
    val lng1 = int("lng1").bindTo { it.lng1 }
    val lng2 = int("lng2").bindTo { it.lng2 }
    val lng3 = int("lng3").bindTo { it.lng3 }
    val lat1 = int("lat1").bindTo { it.lat1 }
    val lat2 = int("lat2").bindTo { it.lat2 }
    val lat3 = int("lat3").bindTo { it.lat3 }
    val informer = varchar("informer").bindTo { it.informer }
    val investigator = varchar("investigator").bindTo { it.investigator }
    val informTime = date("inform_time").bindTo { it.informTime }
    val status = enum<WellStatus>("status").bindTo { it.status }
    val fillStartTime = date("fill_start_time").bindTo { it.fillStartTime }
    val fillEndTime = date("fill_end_time").bindTo { it.fillEndTime }
    // TODO it will be save to database!
//        val remark = text("remark").bindTo { it.remark }
}
