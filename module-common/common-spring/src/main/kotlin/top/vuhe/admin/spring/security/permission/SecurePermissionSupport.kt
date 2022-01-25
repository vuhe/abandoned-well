package top.vuhe.admin.spring.security.permission

import java.io.Serializable
import top.vuhe.admin.spring.property.SecurityProperty
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import top.vuhe.admin.spring.security.principal.UserSecurityService

/**
 * ### 自定义 Security 权限注解实现
 * 直接作为 bean 提供即可注册至配置，配置注册代码在
 * `WebSecurity` 的 `setApplicationContext` 方法
 *
 * @author vuhe
 */
class SecurePermissionSupport(
    private val userDetailsService: UserSecurityService
) : PermissionEvaluator {
    /**
     * 自定义 Security 权限认证 @hasPermission
     */
    override fun hasPermission(authentication: Authentication, targetDomainObject: Any?, permission: Any?): Boolean {
        // 无用户直接 false
        val userId = authentication.principal as String?
        if (userId == null || userId.isEmpty()) return false

        // 获取用户权限 code 列表
        val user = userDetailsService.getLoginUserById(userId)

        // 超级用户跳过检查
        if (SecurityProperty.superAuthOpen && user.isAdmin) {
            return true
        }

        return user.authorities.contains(permission)
    }

    /**
     * ACL 权限控制，此系统不使用
     */
    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        return false
    }
}
