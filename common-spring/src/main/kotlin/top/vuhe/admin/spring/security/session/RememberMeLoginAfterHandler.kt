package top.vuhe.admin.spring.security.session

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import top.vuhe.admin.spring.security.principal.UserSecurityService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RememberMeLoginAfterHandler(
    private val securityService: UserSecurityService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        val userId = authentication.principal as String
        securityService.loginRecord(userId, "Remember Me 登录", true, "")

        // Remember Me 老用户登录续期
        SecuritySessionManager.refreshLastRequest(request.session.id)
    }
}
