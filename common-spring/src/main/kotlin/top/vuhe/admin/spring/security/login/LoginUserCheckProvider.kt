package top.vuhe.admin.spring.security.login

import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import top.vuhe.admin.spring.security.SpringSecurityService
import top.vuhe.admin.spring.security.principal.UserLoggedToken
import kotlin.reflect.full.isSubclassOf

/**
 * 登录信息验证处理
 *
 * @author vuhe
 */
class LoginUserCheckProvider(
    private val service: SpringSecurityService
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
        val user = service.buildLoginUser(username)
            ?: throw UsernameNotFoundException("用户名不存在")

        // 检查用户是否为异常情况
        if (!user.isNonLocked) throw LockedException("用户冻结")
        if (!user.isEnable) throw DisabledException("用户未启用")

        // 验证密码
        val password = authentication.credentials?.toString() ?: throw BadCredentialsException("密码为空(null)")
        if (!passwordEncoder.matches(password, user.password)) throw BadCredentialsException("密码错误")

        return UserLoggedToken(user, authentication.credentials, authentication.details)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.kotlin.isSubclassOf(UsernamePasswordAuthenticationToken::class)
    }
}
