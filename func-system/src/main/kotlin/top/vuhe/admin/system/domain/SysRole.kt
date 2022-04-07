package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 角色领域模型
 *
 * @author vuhe
 */
class SysRole : BaseEntity() {
    /** 编号 */
    var roleId by varchar("role_id").primary()

    /** 角色名称 */
    var roleName by varchar("role_name")

    /** 角色值 */
    var roleCode by varchar("role_code")

    /** 状态 */
    var enable by boolean("enable")

    /** 描述 */
    var details by varchar("details")

    /** 排序 */
    var sort by int("sort")

    /**
     * 提供前端的选中显示，默认为不选中(false)
     */
    var checked: Boolean = false
}
