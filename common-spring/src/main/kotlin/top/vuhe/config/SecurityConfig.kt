package top.vuhe.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl
import top.vuhe.security.SpringSecurityAdapter
import top.vuhe.security.SpringSecurityService
import kotlin.time.Duration.Companion.hours

/**
 * ### Security 安全配置
 * 整个系统中不直接使用实体，使用提供的 LoginUser， 此接口中的 username 为用户 id
 *
 * @author vuhe
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(service: SpringSecurityService) : SpringSecurityAdapter(service) {
    /** 身份认证接口 */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(loginUserCheck)
    }

    /** 配置 Security 控制逻辑 */
    override fun configure(http: HttpSecurity) = http {
        authorizeRequests {
            // 开放登录接口
            authorize("/login/**", permitAll)
            // 开放验证码接口
            authorize("/system/captcha/**", permitAll)
            // 开放静态资源
            authorize("/assets/**", permitAll)
            authorize("/admin/**", permitAll)
            authorize("/component/**", permitAll)
            authorize("/favicon.ico", permitAll)
            // 其他的需要登录后才能访问
            authorize()
        }

        formLogin {
            // 登录接口
            loginPage = "/login"
            // 登录接口
            loginProcessingUrl = "/login"
            // 登录验证后处理
            authenticationSuccessHandler = loginSuccessHandle
            authenticationFailureHandler = loginFailHandle
            // 附加验证码等信息
            authenticationDetailsSource = loginCaptchaCheck
        }

        logout {
            addLogoutHandler(logoutHandle)
            deleteCookies("JSESSIONID")
            logoutSuccessHandler = logoutSuccessHandle
        }

        exceptionHandling {
            accessDeniedHandler = accessDeniedHandle
        }

        rememberMe {
            rememberMeParameter = "remember-me"
            rememberMeCookieName = "remember-me-token"
            authenticationSuccessHandler = rememberMeHandle
            tokenRepository = InMemoryTokenRepositoryImpl()
            key = "VUHE_REMEMBER"
            tokenValiditySeconds = 6.hours.inWholeSeconds.toInt()
        }

        sessionManagement {
            sessionFixation { migrateSession() }
            // 在需要使用到session时才创建session
            sessionCreationPolicy = SessionCreationPolicy.IF_REQUIRED
            sessionConcurrency {
                // 同时登陆多个只保留一个
                maximumSessions = 1
                maxSessionsPreventsLogin = false
                // 踢出用户操作
                expiredSessionStrategy = sessionRegistryCenter
                // 用于统计在线
                sessionRegistry = sessionRegistryCenter
            }
        }

        // 取消跨站请求伪造防护
        csrf { disable() }

        // 防止iframe 造成跨域
        headers { frameOptions { disable() } }
    }
}
