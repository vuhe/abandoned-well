package top.vuhe.security.principal

/**
 * 用户登录信息
 *
 * @author vuhe
 */
interface LoginUser {
    /**
     * 用户 id
     */
    val userId: String

    /**
     * 用于登录验证的密码
     */
    val password: String

    /**
     * 是否为超级管理员
     */
    val isAdmin: Boolean

    /**
     * 用于鉴权的列表
     */
    val authorities: Collection<String>

    /**
     * 用户是否锁定
     */
    val isNonLocked: Boolean

    /**
     * 用户是否启用
     */
    val isEnable: Boolean
}
