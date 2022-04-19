package top.vuhe.admin.spring.security.principal

/**
 * 用于调整 security user 的方法
 *
 * @author vuhe
 */
interface UserSecurityService {
    /**
     * 获取登录用户的 id
     */
    fun getLoginUserId(username: String): String?

    /**
     * 获取登录用户信息
     */
    fun getLoginUserById(userId: String): LoginUser

    fun loginRecord(userId: String, description: String, success: Boolean, errorMsg: String)
}
