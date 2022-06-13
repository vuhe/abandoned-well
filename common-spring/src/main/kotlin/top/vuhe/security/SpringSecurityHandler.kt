package top.vuhe.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import top.vuhe.network.isAjax
import top.vuhe.security.login.LoginPreCaptchaChecker
import top.vuhe.security.login.LoginUserCheckProvider
import top.vuhe.web.HttpServletResponseHandler
import top.vuhe.web.response.AjaxCode

/**
 * 安全组件的自动注册
 *
 * @author vuhe
 */
abstract class SpringSecurityHandler(
    private val service: SpringSecurityService
) : HttpServletResponseHandler() {

    protected val loginSuccessHandle = AuthenticationSuccessHandler { _, response, authentication ->
        val userId = authentication.principal as String
        service.loginSuccess(userId, "表单登录成功")
        response.success(message = "登录成功")
    }

    protected val loginFailHandle = AuthenticationFailureHandler { _, response, e ->
        val msg = e.message ?: "登录失败"
        service.loginFail("表单登录失败", msg)
        response.fail(message = msg)
    }

    protected val logoutHandle = LogoutHandler { request, _, _ ->
        // 销毁session
        request.getSession(false)?.invalidate()
        // 清空Authentication
        SecurityContextHolder.getContext().authentication = null
    }

    protected val logoutSuccessHandle = LogoutSuccessHandler { _, response, _ ->
        SecurityContextHolder.clearContext()
        response.success(message = "注销成功")
    }

    protected val accessDeniedHandle = AccessDeniedHandler { request, response, _ ->
        response.run {
            if (request.isAjax) fail(code = AjaxCode.AccessDenied, message = "暂无权限")
            else sendRedirect("/error/403")
        }
    }

    protected val rememberMeHandle = AuthenticationSuccessHandler { request, _, authentication ->
        val userId = authentication.principal as String
        service.loginSuccess(userId, "Remember Me 登录")

        // Remember Me 老用户登录续期
        SecuritySessionManager.refreshLastRequest(request.session.id)
    }

    protected val loginCaptchaCheck = LoginPreCaptchaChecker

    protected val loginUserCheck = LoginUserCheckProvider(service)

    protected val sessionRegistryCenter = SecuritySessionManager
}
