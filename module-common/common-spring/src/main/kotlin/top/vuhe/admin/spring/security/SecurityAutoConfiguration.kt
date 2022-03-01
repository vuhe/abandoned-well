package top.vuhe.admin.spring.security

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.session.HttpSessionEventPublisher
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.spring.config.SecureConfiguration
import top.vuhe.admin.spring.security.login.LoginAfterHandler
import top.vuhe.admin.spring.security.login.LoginAuthenticationProvider
import top.vuhe.admin.spring.security.login.LoginDetailsSource
import top.vuhe.admin.spring.security.logout.SecureLogoutHandler
import top.vuhe.admin.spring.security.permission.SecureAccessDeniedHandler
import top.vuhe.admin.spring.security.permission.SecurePermissionSupport
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.security.session.RememberMeLoginAfterHandler
import top.vuhe.admin.spring.security.session.SecuritySessionRegistry
import top.vuhe.admin.spring.security.session.SessionExpiredHandler

/**
 * 安全组件的自动注册
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SecureConfiguration::class)
class SecurityAutoConfiguration(
    private val sysLogService: LoggingFactory,
    private val userDetailsService: UserSecurityService,
) {
    @Bean
    fun loginAfterHandler() = LoginAfterHandler(sysLogService, userDetailsService)

    @Bean
    fun loginAuthenticationProvider() = LoginAuthenticationProvider(userDetailsService)

    @Bean
    fun loginDetailSource() = LoginDetailsSource

    @Bean
    fun logoutHandler(httpSessionEventPublisher: HttpSessionEventPublisher) =
        SecureLogoutHandler(httpSessionEventPublisher)

    @Bean
    fun accessDeniedHandler() = SecureAccessDeniedHandler

    @Bean
    fun permissionSupport() = SecurePermissionSupport(userDetailsService)

    @Bean
    fun rememberLoginAfterHandler() = RememberMeLoginAfterHandler(sysLogService, userDetailsService)

    @Bean
    fun sessionRegistry() = SecuritySessionRegistry

    @Bean
    fun sessionExpiredHandler() = SessionExpiredHandler(userDetailsService)

    /**
     * 注册HttpSessionEventPublisher，发布HttpSessionEvent
     */
    @Bean
    fun httpSessionEventPublisher() = HttpSessionEventPublisher()
}
