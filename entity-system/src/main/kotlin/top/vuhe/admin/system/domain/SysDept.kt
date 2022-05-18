package top.vuhe.admin.system.domain

import org.ktorm.entity.Entity

/**
 * 字典值领域模型
 *
 * @author vuhe
 */
interface SysDept : Entity<SysDept> {
    /** 部门编号 */
    var deptId: String

    /** 部门名称 */
    val deptName: String

    /** 部门地址 */
    val address: String

    /** 父级编号 */
    val parentId: String

    /** 负责人 */
    val leader: String

    /** 手机号 */
    val phone: String

    /** 邮箱 */
    val email: String

    /** 状态 */
    var enable: Boolean

    /** 排序 */
    val sort: Int
}
