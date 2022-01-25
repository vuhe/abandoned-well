package top.vuhe.admin.spring.security.login

import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import top.vuhe.admin.api.exception.CaptchaException
import top.vuhe.admin.spring.security.principal.UserSecurityService

/**
 * 登录信息验证处理
 *
 * @author vuhe
 */
class LoginAuthenticationProvider(
    private val userDetailsService: UserSecurityService
) : AuthenticationProvider {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun authenticate(authentication: Authentication): Authentication {
        require(authentication is UsernamePasswordAuthenticationToken) { "不支持的登录信息!" }

        // 首先检查验证码
        val detail = authentication.details as LoginDetailsSource.LoginParameterDetail
        if (detail.captchaEmpty) throw CaptchaException("验证码不能为空!")
        if (!detail.captchaChecked) throw CaptchaException("验证码错误!")

        // 获取数据库用户数据
        val username = authentication.name ?: throw UsernameNotFoundException("")
        val userId = userDetailsService.getLoginUserId(username)
            ?: throw UsernameNotFoundException("")

        val dbUser = userDetailsService.getLoginUserById(userId)

        // 检查用户是否为异常情况
//        if (!dbUser.isNonExpired) throw AccountExpiredException("")
        if (!dbUser.isNonLocked) throw LockedException("")
        if (!dbUser.isEnable) throw DisabledException("")

        // 验证密码
        val password = authentication.credentials?.toString() ?: throw BadCredentialsException("")
        if (!passwordEncoder.matches(password, dbUser.password)) throw BadCredentialsException("")

        return UsernamePasswordAuthenticationToken(
            dbUser.userId, authentication.credentials, emptyList()
        ).apply {
            details = authentication.details
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
