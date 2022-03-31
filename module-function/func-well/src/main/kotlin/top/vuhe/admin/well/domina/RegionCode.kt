package top.vuhe.admin.well.domina

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 二级水文地质分区代码
 *
 * @author vuhe
 */
class RegionCode : BaseEntity() {
    /** 主键 id */
    var id by varchar("id").primary()

    /** 分区代码 */
    var code by varchar("code")

    /** 分区信息 */
    var remark by text("remark")

    /** 是否选中，用于前端显示 */
    var checked: Boolean = false
}
