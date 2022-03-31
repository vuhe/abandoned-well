package top.vuhe.admin.well.domina

import org.hibernate.validator.constraints.Range
import top.vuhe.admin.spring.database.entity.BaseEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * 废弃井信息
 *
 * @author vuhe
 */
class WellInfo : BaseEntity() {
    /** 主键 id */
    var id by varchar("id").primary()

    /** 废弃井名称 */
    var name by varchar("name")

    /** 原始编号 */
    var originCode by varchar("origin_code")

    /** 废弃井类型 */
    var wellType by enum("well_type", WellType.KU)

    /** 废弃井区域 id */
    var regionId by varchar("region_id")

    /** 乡镇(街道办) */
    var street by varchar("street")

    /** 村(街)、门牌号 */
    var address by varchar("address")

    /** 井所属单位 */
    var company by varchar("company")

    /** 建井时间 */
    var digTime by date("dig_time")

    /** 联系人 */
    var contacts by varchar("contacts")

    /** 联系电话 */
    var phone by varchar("phone")

    /** 废弃原因 */
    var abandonRemark by text("abandon_remark")

    /** 废弃时间 */
    var abandonTime by date("abandon_time")

    /** 经度-度 */
    @get:NotNull(message = "废弃井经度(分)未填报")
    @get:Range(message = "废弃井经度超出河南省边界，范围在[110-116]之内", min = 110L, max = 116L)
    var lng1 by int("lng1")

    /** 经度-分 */
    @get:NotNull(message = "废弃井纬度(分)未填报")
    @get:Range(message = "废弃井经度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lng2 by int("lng2")

    /** 经度-秒 */
    @get:NotNull(message = "废弃井经度(秒)未填报")
    @get:Range(message = "废弃井经度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lng3 by int("lng3")

    /** 纬度-度 */
    @get:NotNull(message = "废弃井纬度(度)未填报")
    @get:Range(message = "废弃井纬度超出河南省边界，范围在[31-36]之内", min = 31L, max = 36L)
    var lat1 by int("lat1")

    /** 纬度-分 */
    @get:NotNull(message = "废弃井纬度(分)未填报")
    @get:Range(message = "废弃井纬度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lat2 by int("lat2")

    /** 纬度-秒 */
    @get:NotNull(message = "废弃井纬度(秒)未填报")
    @get:Range(message = "废弃井纬度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lat3 by int("lat3")

    /** 填表人 */
    @get:NotBlank(message = "填表人未填报")
    @get:Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "填表人填报有误")
    var informer by varchar("informer")

    /** 排查人 */
    @get:NotBlank(message = "排查人未填报")
    @get:Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "排查人填报有误")
    var investigator by varchar("investigator")

    /** 填表时间 */
    @get:NotNull(message = "填表时间未填报")
    var informTime by date("inform_time")

    /** 信息状态 */
    var status by enum("status", WellStatus.Reported)

    /** 回填开始时间 */
    var fillStartTime by date("fill_start_time").nullable()

    /** 回填结束时间 */
    var fillEndTime by date("fill_end_time").nullable()

    // TODO it will be save to database!
    /** 注释 */
    var remark: String = ""

    /* ------------------------------------- 辅助字段 -------------------------------------- */

    /** 经度 */
    val lng: String get() = "${lng1}°${lng2}′${lng3}″"

    /** 纬度 */
    val lat: String get() = "${lat1}°${lat2}′${lat3}″"

    /** 井类型字符串 */
    val type: String get() = "${wellType.category}-${wellType.type}"

    /** 井状态字符串 */
    val statusStr: String
        get() = when (status) {
            WellStatus.NotAccepted -> "审核不通过（打回修改）"
            WellStatus.Approved -> "审核通过"
            WellStatus.Reported -> "已上报（审核中）"
        }
}
