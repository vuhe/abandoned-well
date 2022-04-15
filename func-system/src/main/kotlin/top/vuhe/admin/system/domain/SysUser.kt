package top.vuhe.admin.system.domain

import org.ktorm.entity.Entity
import java.time.LocalDateTime

/**
 * ### 用户领域模型
 *
 * @author vuhe
 */
interface SysUser : Entity<SysUser> {
    /** 编号 */
    var userId: String

    /** 账户 */
    val username: String

    /** 密码 */
    var password: String

    /** 姓名 */
    val realName: String

    /** 邮箱 */
    val email: String

    /** 性别 */
    val sex: String

    /** 电话 */
    val phone: String

    /** 所属部门 */
    val deptId: String

    /** 是否锁定 */
    val unlocked: Boolean

    /** 是否启用 */
    var enable: Boolean

    /** 是否为管理员 */
    val admin: Boolean

    /** 最后一次登录时间 */
    var lastTime: LocalDateTime?

    /** 注册时间 */
    var createTime: LocalDateTime

    /** 备注 */
    val remark: String

    /** 角色 ids，用于修改 */
    val roles: List<String>
        get() = get("roleIds")?.toString()?.split(",") ?: emptyList()
}
