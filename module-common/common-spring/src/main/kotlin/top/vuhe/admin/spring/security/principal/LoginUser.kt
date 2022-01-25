package top.vuhe.admin.spring.security.principal

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority

/**
 * 用户登录信息
 *
 * @author vuhe
 */
interface LoginUser: CredentialsContainer {
    /**
     * 用户 id
     */
    val userId: String

    /**
     * 用于登录验证的密码
     */
    val password: String

    /**
     * 前端显示昵称
     */
    val nickname: String

    /**
     * 是否为超级管理员
     */
    val isAdmin: Boolean

    /**
     * 用于鉴权的列表
     */
    val authorities: Set<GrantedAuthority>

    /**
     * 用户是否锁定
     */
    val isNonLocked: Boolean

    /**
     * 用户是否启用
     */
    val isEnable: Boolean

    /**
     * 登录是否过期，过期登录权限验证失败
     */
    val isLoginNonExpired: Boolean
}
