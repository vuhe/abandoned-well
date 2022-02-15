package top.vuhe.admin.well.domina

import java.time.LocalDate

/**
 * 用于导出井信息的对象
 *
 * @author vuhe
 */
data class ExportWell(
    val id: String,
    val name: String,
    val originCode: String,
    val category: String,
    val type: String,
    val city: String,
    val county: String,
    val street: String,
    val address: String,
    val company: String,
    val digTime: String,
    val contacts: String,
    val phone: String,
    val abandonRemark: String,
    val abandonTime: String,
    val lng: String,
    val lat: String,
    val informer: String,
    val investigator: String,
    val informTime: String,
    val fillStartTime: String,
    val fillEndTime: String,
    val status: String,
    val remark: String = "",
    val info: String = ""
) {
    constructor(well: WellInfo, region: WellRegion) : this(
        id = well.id, name = well.name, originCode = well.originCode,
        category = well.wellType?.category ?: "", type = well.wellType?.type ?: "",
        city = region.city, county = region.county, street = well.street, address = well.address,
        company = well.company, digTime = dateFormatter(well.digTime), contacts = well.contacts,
        phone = well.phone, abandonRemark = well.abandonRemark, abandonTime = dateFormatter(well.abandonTime),
        lng = well.lng, lat = well.lat, informer = well.informer, investigator = well.investigator,
        informTime = dateFormatter(well.informTime), fillStartTime = dateFormatter(well.fillStartTime),
        fillEndTime = dateFormatter(well.fillEndTime), status = well.statusStr
    )

    companion object {
        private fun dateFormatter(date: LocalDate?): String {
            return if (date == null) "未填报"
            else "%04d%02d".format(date.year, date.monthValue)
        }
    }
}
