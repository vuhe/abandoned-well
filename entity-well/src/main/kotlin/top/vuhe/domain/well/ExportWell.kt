package top.vuhe.domain.well

import top.vuhe.domain.region.WellRegion
import top.vuhe.office.OfficeData
import java.time.LocalDate

/**
 * ## 用于导出井信息的对象
 *
 * @author vuhe
 */
class ExportWell(private val well: WellInfo, private val region: WellRegion) : OfficeData {
    override val data: Map<String, Any> = buildMap {
        put("id", well.id)
        put("name", well.name)
        put("originCode", well.originCode)
        put("category", well.wellType.category)
        put("type", well.wellType.type)
        put("city", region.city)
        put("county", region.county)
        put("street", well.street)
        put("address", well.address)
        put("company", well.company)
        put("digTime", dateFormatter(well.digTime))
        put("contacts", well.contacts)
        put("phone", well.phone)
        put("abandonRemark", well.abandonRemark)
        put("abandonTime", dateFormatter(well.abandonTime))
        put("lng", well.lng)
        put("lat", well.lat)
        put("informer", well.informer)
        put("investigator", well.investigator)
        put("informTime", dateFormatter(well.informTime))
        put("fillStartTime", dateFormatter(well.fillStartTime))
        put("fillEndTime", dateFormatter(well.fillEndTime))
        put("status", well.status.tag)
        put("remark", well.remark.ifBlank { "无" })
    }

    private fun dateFormatter(date: LocalDate?): String {
        return if (date == null) "未填报"
        else "%04d%02d".format(date.year, date.monthValue)
    }
}
