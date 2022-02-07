package top.vuhe.admin.well.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.domina.WellStatus
import top.vuhe.admin.well.domina.WellType

/**
 * 井信息接口
 *
 * @author vuhe
 */
@Repository
class WellMapper : CurdMapper<WellInfo>("well_info") {
    override val id = varchar("id").primaryKey().bind(WellInfo::id)
    private val name = varchar("name").bind(WellInfo::name)
    private val originCode = varchar("origin_code").bind(WellInfo::originCode)
    private val wellType = enum<WellType>("well_type").bind(WellInfo::wellType)
    private val regionId = varchar("region_id").bind(WellInfo::regionId)
    private val street = varchar("street").bind(WellInfo::street)
    private val address = varchar("address").bind(WellInfo::address)
    private val company = varchar("company").bind(WellInfo::company)
    private val digTime = datetime("dig_time").bind(WellInfo::digTime)
    private val contacts = varchar("contacts").bind(WellInfo::contacts)
    private val phone = varchar("phone").bind(WellInfo::phone)
    private val abandonRemark = text("abandon_remark").bind(WellInfo::abandonRemark)
    private val abandonTime = datetime("abandon_time").bind(WellInfo::abandonTime)
    private val lng = varchar("lng").bind(WellInfo::lng)
    private val lat = varchar("lat").bind(WellInfo::lat)
    private val informer = varchar("informer").bind(WellInfo::informer)
    private val investigator = varchar("investigator").bind(WellInfo::investigator)
    private val informTime = datetime("inform_time").bind(WellInfo::informTime)
    private val status = enum<WellStatus>("status").bind(WellInfo::status, WellStatus.Reported)
    private val fillStartTime = datetime("fill_start_time").bind(WellInfo::fillStartTime)
    private val fillEndTime = datetime("fill_end_time").bind(WellInfo::fillEndTime)

    override fun Query.listFilter(param: WellInfo): Query {
        return whereWithConditions {
            if (param.name.isNotEmpty()) it.add(name like "%${param.name}%")
        }
    }
}
