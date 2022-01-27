package top.vuhe.admin.well.domina

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 二级水文地质分区代码
 *
 * @author vuhe
 */
class RegionCode: BaseEntity() {
    /** 主键 id */
    override var id: String = ""

    /** 分区代码 */
    var code: String = ""
}
