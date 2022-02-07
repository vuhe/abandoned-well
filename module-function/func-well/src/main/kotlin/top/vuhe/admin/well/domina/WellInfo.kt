package top.vuhe.admin.well.domina

import top.vuhe.admin.spring.database.entity.BaseEntity
import java.time.LocalDateTime

/**
 * 废弃井信息
 *
 * @author vuhe
 */
class WellInfo : BaseEntity() {
    /** 主键 id */
    override var id: String = ""

    /** 废弃井名称 */
    var name: String = ""

    /** 原始编号 */
    var originCode: String = ""

    /** 废弃井类型 */
    var wellType: WellType? = null

    /** 废弃井区域 id */
    var regionId: String = ""

    /** 乡镇(街道办) */
    var street: String = ""

    /** 村(街)、门牌号 */
    var address: String = ""

    /** 井所属单位 */
    var company: String = ""

    /** 建井时间 */
    var digTime: LocalDateTime? = null

    /** 联系人 */
    var contacts: String = ""

    /** 联系电话 */
    var phone: String = ""

    /** 废弃原因 */
    var abandonRemark: String = ""

    /** 废弃时间 */
    var abandonTime: LocalDateTime? = null

    /** 经度 */
    var lng: String = ""

    /** 纬度-度 */
    var lat: String = ""

    /** 填表人 */
//    @NotBlank(message = "填表人未填报")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{0,}$", message = "填表人填报有误")
    var informer: String = ""

    /** 排查人 */
//    @NotBlank(message = "排查人未填报")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{0,}$", message = "排查人填报有误")
    var investigator: String = ""

    /** 填表时间 */
//    @NotNull(message = "报出时间未填报")
    var informTime: LocalDateTime? = null

    /** 信息状态 */
    var status: WellStatus? = null

    /** 回填开始时间 */
    var fillStartTime: LocalDateTime? = null

    /** 回填结束时间 */
    var fillEndTime: LocalDateTime? = null

    /* ------------------------------------- json 解析字段-------------------------------------- */

    /** 经度-度 */
//    @NotNull(message = "废弃井经度(分)未填报")
//    @Range(message = "废弃井经度超出河南省边界，范围在[110-116]之内", min = 110L, max = 116L)
    var lng1: Int? = null

    /** 经度-分 */
//    @NotNull(message = "废弃井纬度(分)未填报")
//    @Range(message = "废弃井经度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lng2: Int? = null

    /** 经度-秒 */
//    @NotNull(message = "废弃井经度(秒)未填报")
//    @Range(message = "废弃井经度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lng3: Int? = null

    /** 纬度-度 */
//    @NotNull(message = "废弃井纬度(度)未填报")
//    @Range(message = "废弃井纬度超出河南省边界，范围在[31-36]之内", min = 31L, max = 36L)
    var lat1: Int? = null

    /** 纬度-分 */
//    @NotNull(message = "废弃井纬度(分)未填报")
//    @Range(message = "废弃井纬度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lat2: Int? = null

    /** 纬度-秒 */
//    @NotNull(message = "废弃井纬度(秒)未填报")
//    @Range(message = "废弃井纬度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    var lat3: Int? = null

    /** 井类型字符串 */
    val type: String get() = "${wellType?.category}-${wellType?.type}"

    /** 井状态字符串 */
    val statusStr: String
        get() = when (status) {
            WellStatus.NotAccepted -> "审核不通过（打回修改）"
            WellStatus.Approved -> "审核通过"
            else -> "已上报（审核中）"
        }
}
