package top.vuhe.admin.spring.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import top.vuhe.admin.spring.security.login.LoginAfterHandler
import top.vuhe.admin.spring.security.login.LoginPreCaptchaChecker
import top.vuhe.admin.spring.security.login.LoginUserCheckProvider
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
    objectMapper: ObjectMapper, userDetailsService: UserSecurityService,
) : WebSecurityConfigurerAdapter() {
    protected val loginAfterHandler = LoginAfterHandler(objectMapper, userDetailsService)
    protected val loginAuthenticationProvider = LoginUserCheckProvider(userDetailsService)
    protected val loginDetailSource = LoginPreCaptchaChecker
    protected val logoutHandler = SecureLogoutHandler(objectMapper)
    protected val accessDenied = SecureAccessDeniedHandler(objectMapper)
    protected val rememberLoginAfterHandler = RememberMeLoginAfterHandler(userDetailsService)
    protected val sessionRegistryCenter = SecuritySessionManager

    /**
     * 使 listener 生效
     */
    @Bean
    open fun securitySessionListener() = sessionRegistryCenter

    /**
     * 禁止其他自动注入
     */
    @Bean
    open fun loginAuthenticationProvider() = loginAuthenticationProvider
}
