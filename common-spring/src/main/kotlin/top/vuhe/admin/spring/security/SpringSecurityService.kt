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
     * 记录登录成功信息
     */
    fun loginSuccess(userId: String, description: String)

    /**
     * 记录登录失败信息
     */
    fun loginFail(userId: String, description: String, errorMsg: String)
}
