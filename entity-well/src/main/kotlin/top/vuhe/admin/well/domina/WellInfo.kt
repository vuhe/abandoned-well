package top.vuhe.admin.well.domina

import org.hibernate.validator.constraints.Range
import org.ktorm.entity.Entity
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * 废弃井信息
 *
 * @author vuhe
 */
interface WellInfo : Entity<WellInfo> {
    /** 主键 id */
    var id: String

    /** 废弃井名称 */
    val name: String

    /** 原始编号 */
    val originCode: String

    /** 废弃井类型 */
    val wellType: WellType

    /** 废弃井区域 id */
    val regionId: String

    /** 乡镇(街道办) */
    val street: String

    /** 村(街)、门牌号 */
    val address: String

    /** 井所属单位 */
    val company: String

    /** 建井时间 */
    val digTime: LocalDate

    /** 联系人 */
    val contacts: String

    /** 联系电话 */
    val phone: String

    /** 废弃原因 */
    val abandonRemark: String

    /** 废弃时间 */
    val abandonTime: LocalDate

    /** 经度-度 */
    @get:NotNull(message = "废弃井经度(分)未填报")
    @get:Range(message = "废弃井经度超出河南省边界，范围在[110-116]之内", min = 110L, max = 116L)
    val lng1: Int

    /** 经度-分 */
    @get:NotNull(message = "废弃井纬度(分)未填报")
    @get:Range(message = "废弃井经度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    val lng2: Int

    /** 经度-秒 */
    @get:NotNull(message = "废弃井经度(秒)未填报")
    @get:Range(message = "废弃井经度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    val lng3: Int

    /** 纬度-度 */
    @get:NotNull(message = "废弃井纬度(度)未填报")
    @get:Range(message = "废弃井纬度超出河南省边界，范围在[31-36]之内", min = 31L, max = 36L)
    val lat1: Int

    /** 纬度-分 */
    @get:NotNull(message = "废弃井纬度(分)未填报")
    @get:Range(message = "废弃井纬度（分）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    val lat2: Int

    /** 纬度-秒 */
    @get:NotNull(message = "废弃井纬度(秒)未填报")
    @get:Range(message = "废弃井纬度（秒）填报有误，范围在[0-60)之内", min = 0, max = 59L)
    val lat3: Int

    /** 填表人 */
    @get:NotBlank(message = "填表人未填报")
    @get:Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "填表人填报有误")
    val informer: String

    /** 排查人 */
    @get:NotBlank(message = "排查人未填报")
    @get:Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "排查人填报有误")
    val investigator: String

    /** 填表时间 */
    @get:NotNull(message = "填表时间未填报")
    val informTime: LocalDate

    /** 信息状态 */
    var status: WellStatus

    /** 回填开始时间 */
    val fillStartTime: LocalDate?

    /** 回填结束时间 */
    val fillEndTime: LocalDate?

    /** 注释 */
    val remark: String

    /* ------------------------------------- 辅助字段 -------------------------------------- */

    /** 经度 */
    val lng: String get() = "${lng1}°${lng2}′${lng3}″"

    /** 纬度 */
    val lat: String get() = "${lat1}°${lat2}′${lat3}″"

}
