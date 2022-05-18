package top.vuhe.admin.well.domina

import org.hibernate.validator.constraints.Length
import org.ktorm.entity.Entity

/**
 * 废弃井区域信息
 *
 * @author vuhe
 */
interface WellRegion : Entity<WellRegion> {
    /** 主键 id */
    var id: String

    /** 省辖市（含济源） */
    val city: String

    /** 区/县 */
    val county: String

    /** 行政区划代码 */
    @get:Length(min = 6, max = 6, message = "行政区划代码应为6位")
    val districtCode: String

    /** 二级水文地址代码 id */
    val regionCodeId: String

    /** 排序码 */
    val sort: Int

    /** 当前区域统计井数(用于创建井编号) */
    var next: Int
}
