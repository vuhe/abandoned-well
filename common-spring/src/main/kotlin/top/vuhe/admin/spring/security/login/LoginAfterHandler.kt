package top.vuhe.admin.spring.security.login

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.web.HttpServletResponseHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 自定义 Security 用户登录处理类
 *
 * @author vuhe
 */
class LoginAfterHandler(
    objectMapper: ObjectMapper,
    private val securityService: UserSecurityService,
) : HttpServletResponseHandler(objectMapper), AuthenticationFailureHandler, AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userId = authentication.principal as String
        securityService.loginRecord(userId, "表单登录成功", true, "")
        response.success(message = "登录成功")
    }

    override fun onAuthenticationFailure(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        val msg = e.message ?: "登录失败"
        securityService.loginRecord("", "表单登录失败", false, msg)
        httpServletResponse.fail(message = msg)
    }
}
