package top.vuhe.admin.spring.security.session

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.security.principal.UserSecurityService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RememberMeLoginAfterHandler(
    private val sysLogService: LoggingFactory,
    private val sysUserService: UserSecurityService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        // 记录日志
        sysLogService.record {
            it.title = "Remember Me"
            it.description = "登录成功"
            it.businessType = BusinessType.OTHER
            it.success = true
            it.loggingType = LoggingType.LOGIN
        }

        val userId = authentication.principal as String
        sysUserService.updateLoginTime(userId)

        // Remember Me 老用户登录续期
        SecuritySessionManager.refreshLastRequest(request.session.id)
    }
}
