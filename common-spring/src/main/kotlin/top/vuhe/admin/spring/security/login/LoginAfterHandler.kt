package top.vuhe.admin.spring.security.login

import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.web.response.fail
import top.vuhe.admin.spring.web.response.success
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 自定义 Security 用户登录处理类
 *
 * @author vuhe
 */
class LoginAfterHandler(
    private val sysLogService: LoggingFactory,
    private val sysUserService: UserSecurityService,
) : AuthenticationFailureHandler, AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        sysLogService.record {
            it.title = "登录"
            it.description = "登录成功"
            it.businessType = BusinessType.OTHER
            it.success = true
            it.loggingType = LoggingType.LOGIN
        }

        val userId = authentication.principal as String
        sysUserService.updateLoginTime(userId)

        response.success(message = "登录成功")
    }

    override fun onAuthenticationFailure(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        val msg = e.message ?: "登录失败"
        sysLogService.record {
            it.title = "登录"
            it.description = msg
            it.businessType = BusinessType.OTHER
            it.success = false
            it.loggingType = LoggingType.LOGIN
        }
        httpServletResponse.fail(code = 500, message = msg)
    }
}
