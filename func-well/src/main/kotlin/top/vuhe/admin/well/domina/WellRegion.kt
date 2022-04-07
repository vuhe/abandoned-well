package top.vuhe.admin.well.domina

import org.hibernate.validator.constraints.Length
import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 废弃井区域信息
 *
 * @author vuhe
 */
class WellRegion : BaseEntity() {
    /** 主键 id */
    var id by varchar("id").primary()

    /** 省辖市（含济源） */
    var city by varchar("city")

    /** 区/县 */
    var county by varchar("county")

    /** 行政区划代码 */
    @get:Length(min = 6, max = 6, message = "行政区划代码应为6位")
    var districtCode by varchar("district_code")

    /** 二级水文地址代码 id */
    var regionCodeId by varchar("region_code")

    /** 排序码 */
    var sort by int("sort")

    /** 当前区域统计井数(用于创建井编号) */
    var next by int("count")
}
