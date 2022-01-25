package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 角色领域模型
 *
 * @author vuhe
 */
class SysRole : BaseEntity() {
    override val id: String get() = roleId

    /**
     * 编号
     */
    var roleId: String = ""

    /**
     * 角色名称
     */
    var roleName: String = ""

    /**
     * 角色值
     */
    var roleCode: String = ""

    /**
     * 状态
     */
    var enable: Boolean? = null

    /**
     * 描述
     */
    var details: String = ""

    /**
     * 排序
     */
    var sort: Int? = null

    /**
     * 提供前端的选中显示，默认为不选中(false)
     */
    var checked: Boolean = false
}
