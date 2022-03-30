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
@Suppress("unused")
class WellMapper : CurdMapper<WellInfo>("well_info") {
    override val id = varchar("id").primaryKey().bind(WellInfo::id)
    private val name = varchar("name").bind(WellInfo::name)
    private val originCode = varchar("origin_code").bind(WellInfo::originCode)
    private val wellType = enum<WellType>("well_type").bind(WellInfo::wellType)
    private val regionId = varchar("region_id").bind(WellInfo::regionId)
    private val street = varchar("street").bind(WellInfo::street)
    private val address = varchar("address").bind(WellInfo::address)
    private val company = varchar("company").bind(WellInfo::company)
    private val digTime = date("dig_time").bind(WellInfo::digTime)
    private val contacts = varchar("contacts").bind(WellInfo::contacts)
    private val phone = varchar("phone").bind(WellInfo::phone)
    private val abandonRemark = text("abandon_remark").bind(WellInfo::abandonRemark)
    private val abandonTime = date("abandon_time").bind(WellInfo::abandonTime)
    private val lng1 = int("lng1").bind(WellInfo::lng1)
    private val lng2 = int("lng2").bind(WellInfo::lng2)
    private val lng3 = int("lng3").bind(WellInfo::lng3)
    private val lat1 = int("lat1").bind(WellInfo::lat1)
    private val lat2 = int("lat2").bind(WellInfo::lat2)
    private val lat3 = int("lat3").bind(WellInfo::lat3)
    private val informer = varchar("informer").bind(WellInfo::informer)
    private val investigator = varchar("investigator").bind(WellInfo::investigator)
    private val informTime = date("inform_time").bind(WellInfo::informTime)
    private val status = enum<WellStatus>("status").bind(WellInfo::status, WellStatus.Reported)
    private val fillStartTime = date("fill_start_time").bind(WellInfo::fillStartTime)
    private val fillEndTime = date("fill_end_time").bind(WellInfo::fillEndTime)

    override fun Query.listFilter(param: WellInfo): Query {
        return whereWithConditions {
            if (param.name.isNotEmpty()) it.add(name like "%${param.name}%")
        }
    }

    /**
     * 使用根据文档规则生成的 id 插入
     */
    override fun insert(entity: WellInfo): Int = database.insert(this) {
        insertSetting(entity)
        set(id, entity.id)
    }

    /**
     * 使用根据文档规则生成的 id 插入
     */
    override fun batchInsert(entities: Collection<WellInfo>): Int = database.batchInsert(this) {
        entities.forEach { e ->
            item {
                insertSetting(e)
                set(id, e.id)
            }
        }
    }.reduce { a, b -> a + b }

}
