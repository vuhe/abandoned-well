package top.vuhe.admin.system.domain

import org.ktorm.entity.Entity

/**
 * 角色领域模型
 *
 * @author vuhe
 */
interface SysRole : Entity<SysRole> {
    /** 编号 */
    var roleId: String

    /** 角色名称 */
    val roleName: String

    /** 角色值 */
    val roleCode: String

    /** 状态 */
    var enable: Boolean

    /** 描述 */
    val details: String

    /** 排序 */
    val sort: Int
}
