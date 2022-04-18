package top.vuhe.admin.spring.security.login

import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import top.vuhe.admin.spring.security.principal.UserAuthenticatedToken
import top.vuhe.admin.spring.security.principal.UserSecurityService
import kotlin.reflect.full.isSubclassOf

/**
 * 登录信息验证处理
 *
 * @author vuhe
 */
class LoginUserCheckProvider(
    private val userDetailsService: UserSecurityService
) : AuthenticationProvider {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun authenticate(authentication: Authentication): Authentication {
        require(authentication is UsernamePasswordAuthenticationToken) {
            val clazz = authentication::class.simpleName
            "Unsupported $clazz class token!"
        }

        // 获取数据库用户数据
        val username = authentication.name
            ?: throw UsernameNotFoundException("用户名为空(null)")
        val userId = userDetailsService.getLoginUserId(username)
            ?: throw UsernameNotFoundException("用户名不存在")

        val dbUser = userDetailsService.getLoginUserById(userId)

        // 检查用户是否为异常情况
        if (!dbUser.isNonLocked) throw LockedException("用户冻结")
        if (!dbUser.isEnable) throw DisabledException("用户未启用")

        // 验证密码
        val password = authentication.credentials?.toString() ?: throw BadCredentialsException("密码为空(null)")
        if (!passwordEncoder.matches(password, dbUser.password)) throw BadCredentialsException("密码错误")

        return UserAuthenticatedToken(dbUser, authentication.credentials, authentication.details)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.kotlin.isSubclassOf(UsernamePasswordAuthenticationToken::class)
    }
}
