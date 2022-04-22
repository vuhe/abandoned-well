package top.vuhe.admin.spring.security.hepler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import top.vuhe.admin.api.network.isAjax
import top.vuhe.admin.spring.security.SpringSecurityService
import top.vuhe.admin.spring.web.HttpServletResponseHandler
import top.vuhe.admin.spring.web.response.AjaxCode

internal class SecurityHandler(
    objectMapper: ObjectMapper, private val service: SpringSecurityService,
) : HttpServletResponseHandler(objectMapper) {

    val loginSuccess = AuthenticationSuccessHandler { _, response, authentication ->
        val userId = authentication.principal as String
        service.loginSuccess(userId, "表单登录成功")
        response.success(message = "登录成功")
    }

    val loginFail = AuthenticationFailureHandler { _, response, e ->
        val msg = e.message ?: "登录失败"
        service.loginFail("", "表单登录失败", msg)
        response.fail(message = msg)
    }

    val logout = LogoutHandler { request, _, _ ->
        // 销毁session
        request.getSession(false)?.invalidate()
        // 清空Authentication
        SecurityContextHolder.getContext().authentication = null
    }

    val logoutSuccess = LogoutSuccessHandler { _, response, _ ->
        SecurityContextHolder.clearContext()
        response.success(message = "注销成功")
    }

    val accessDenied = AccessDeniedHandler { request, response, _ ->
        response.run {
            if (request.isAjax) fail(code = AjaxCode.AccessDenied, message = "暂无权限")
            else sendRedirect("/error/403")
        }
    }

    val rememberMe = AuthenticationSuccessHandler { request, _, authentication ->
        val userId = authentication.principal as String
        service.loginSuccess(userId, "Remember Me 登录")

        // Remember Me 老用户登录续期
        SecuritySessionManager.refreshLastRequest(request.session.id)
    }

}
