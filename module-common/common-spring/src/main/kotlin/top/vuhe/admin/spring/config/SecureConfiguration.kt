package top.vuhe.admin.spring.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.spring.security.SpringSecurityAdapter
import top.vuhe.admin.spring.security.principal.UserSecurityService

/**
 * ### Security 安全配置
 * 整个系统中不直接使用实体，使用提供的 LoginUser，
 * 此接口中的 username 为用户 id
 *
 * @author vuhe
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecureConfiguration(
    sysLogService: LoggingFactory,
    userDetailsService: UserSecurityService
) : SpringSecurityAdapter(sysLogService, userDetailsService) {
    /**
     * 身份认证接口
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(loginAuthenticationProvider)
    }

    /**
     * 配置 Security 控制逻辑
     */
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

        httpBasic {
            authenticationEntryPoint = accessDenied
        }

        formLogin {
            // 登录接口
            loginPage = "/login"
            // 登录接口
            loginProcessingUrl = "/login"
            // 登录验证后处理
            authenticationSuccessHandler = loginAfterHandler
            authenticationFailureHandler = loginAfterHandler
            // 附加验证码等信息
            authenticationDetailsSource = loginDetailSource
        }

        logout {
            addLogoutHandler(logoutHandler)
            // 退出登录删除 cookie缓存
            deleteCookies("JSESSIONID")
            // 配置用户登出自定义处理类
            logoutSuccessHandler = logoutHandler
        }

        exceptionHandling {
            // 配置没有权限自定义处理类
            accessDeniedHandler = accessDenied
        }

        rememberMe {
            rememberMeParameter = "remember-me"
            rememberMeCookieName = "rememberme-token"
            authenticationSuccessHandler = rememberLoginAfterHandler
            tokenRepository = rememberTokenService
            key = "VUHE_REMEMBER"
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
