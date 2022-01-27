package top.vuhe.admin.well.domina

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 废弃井区域信息
 *
 * @author vuhe
 */
class WellRegion : BaseEntity() {
    /** 主键 id */
    override var id: String = ""

    /** 省辖市（含济源） */
    var city: String = ""

    /** 区/县 */
    var county: String = ""

    /** 行政区划代码 */
    var districtCode: String = ""

    /** 二级水文地址代码 id */
    var regionCodeId: String = ""

    /** 排序码 */
    var sort: Int? = null

    /** 当前区域统计井数 */
    var count: Int? = 0
}
