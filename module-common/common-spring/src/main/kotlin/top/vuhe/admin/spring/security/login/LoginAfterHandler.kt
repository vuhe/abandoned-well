package top.vuhe.admin.spring.security.login

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.enums.LoggingType
import top.vuhe.admin.api.exception.CaptchaException
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.network.writeJson
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.web.response.ResultObj
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

        request.session.setAttribute("currentUser", userId)
        val result = ResultObj.Success<Nothing>(message = "登录成功")
        response.writeJson(result)
    }

    override fun onAuthenticationFailure(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        val msg = when (e) {
            is CaptchaException -> e.info
            is UsernameNotFoundException -> "用户名不存在"
            is LockedException -> "用户冻结"
            is AccountExpiredException -> "用户过期"
            is BadCredentialsException -> "账户密码不正确"
            is DisabledException -> "用户未启用"
            else -> "登录失败"
        }
        val result = ResultObj.Fail<Nothing>(code = 500, message = msg)
        sysLogService.record {
            it.title = "登录"
            it.description = result.msg
            it.businessType = BusinessType.OTHER
            it.success = false
            it.loggingType = LoggingType.LOGIN
        }
        httpServletResponse.writeJson(result)
    }
}
