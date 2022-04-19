package top.vuhe.admin.spring.security

import top.vuhe.admin.spring.security.principal.LoginUser

/**
 * spring security 的辅助方法
 *
 * @author vuhe
 */
interface SpringSecurityService {

    /**
     * 获取登录用户信息
     */
    fun buildLoginUser(username: String): LoginUser?

    /**
     * 记录登录信息
     */
    fun loginRecord(userId: String, description: String, success: Boolean, errorMsg: String)
}
