package top.vuhe.admin.spring.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import top.vuhe.admin.spring.security.hepler.SecurityHandler
import top.vuhe.admin.spring.security.hepler.SecuritySessionManager
import top.vuhe.admin.spring.security.login.LoginPreCaptchaChecker
import top.vuhe.admin.spring.security.login.LoginUserCheckProvider

/**
 * 安全组件的自动注册
 *
 * @author vuhe
 */
abstract class SpringSecurityAdapter(userDetailsService: SpringSecurityService) :
    WebSecurityConfigurerAdapter() {
    private val handler = SecurityHandler(userDetailsService)

    protected val loginSuccessHandle = handler.loginSuccess
    protected val loginFailHandle = handler.loginFail

    protected val logoutHandle = handler.logout
    protected val logoutSuccessHandle = handler.logoutSuccess

    protected val accessDeniedHandle = handler.accessDenied

    protected val rememberMeHandle = handler.rememberMe

    protected val loginCaptchaCheck = LoginPreCaptchaChecker
    protected val loginUserCheck = LoginUserCheckProvider(userDetailsService)

    protected val sessionRegistryCenter = SecuritySessionManager

    /** 使 listener 生效 */
    @Bean
    open fun securitySessionListener() = sessionRegistryCenter

    /** 禁止其他自动注入 */
    @Bean
    open fun loginAuthenticationProvider() = loginUserCheck
}
