package top.vuhe.admin.spring.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.spring.security.login.LoginAfterHandler
import top.vuhe.admin.spring.security.login.LoginAuthenticationProvider
import top.vuhe.admin.spring.security.login.LoginDetailsSource
import top.vuhe.admin.spring.security.logout.SecureLogoutHandler
import top.vuhe.admin.spring.security.permission.SecureAccessDeniedHandler
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.security.session.RememberMeLoginAfterHandler
import top.vuhe.admin.spring.security.session.SecuritySessionManager

/**
 * 安全组件的自动注册
 *
 * @author vuhe
 */
abstract class SpringSecurityAdapter(
    sysLogService: LoggingFactory, userDetailsService: UserSecurityService,
) : WebSecurityConfigurerAdapter() {
    protected val loginAfterHandler = LoginAfterHandler(sysLogService, userDetailsService)
    protected val loginAuthenticationProvider = LoginAuthenticationProvider(userDetailsService)
    protected val loginDetailSource = LoginDetailsSource
    protected val logoutHandler = SecureLogoutHandler
    protected val accessDenied = SecureAccessDeniedHandler
    protected val rememberLoginAfterHandler = RememberMeLoginAfterHandler(sysLogService, userDetailsService)
    protected val sessionRegistryCenter = SecuritySessionManager

    /**
     * 使 listener 生效
     */
    @Bean
    fun securitySessionListener() = sessionRegistryCenter

    /**
     * 禁止其他自动注入
     */
    @Bean
    fun loginAuthenticationProvider() = loginAuthenticationProvider
}
