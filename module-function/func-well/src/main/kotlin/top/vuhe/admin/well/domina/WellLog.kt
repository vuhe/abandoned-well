package top.vuhe.admin.well.domina

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 动态更新日志
 *
 * @author vuhe
 */
class WellLog: BaseEntity() {
    /** 主键 id */
    override var id: String = ""

    /** 井信息 id */
    var wellId: String = ""

    /** 更新类型 */
    var logType: String = ""

    /** 更新人名称 */
    var username: String = ""
}
