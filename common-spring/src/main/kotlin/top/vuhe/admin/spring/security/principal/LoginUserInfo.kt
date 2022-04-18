package top.vuhe.admin.spring.security.principal

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 * 从 security context 中获取对象
 *
 * @author vuhe
 */
object LoginUserInfo {
    /**
     * 获取当前登录用户 id，如果未登录返回 ""
     */
    val currUserId: String
        get() = authentication?.principal as String? ?: ""

    /**
     * 验证当前用户是否登录
     */
    val isAuthentication: Boolean get() = authentication != null

    /**
     * 获取当前登录用户的信息
     */
    private val authentication: Authentication?
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return if (authentication !is AnonymousAuthenticationToken) authentication
            else null
        }
}
