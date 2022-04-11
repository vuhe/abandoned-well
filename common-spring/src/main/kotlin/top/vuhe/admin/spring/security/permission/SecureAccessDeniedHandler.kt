package top.vuhe.admin.spring.security.permission

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import top.vuhe.admin.api.network.isAjax
import top.vuhe.admin.spring.web.response.fail
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 自定义 Security 权限处理类
 * - 用户无权限
 * - 用户未登录
 *
 * @author vuhe
 */
object SecureAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) = httpServletResponse.run {
        if (httpServletRequest.isAjax) fail(code = 403, message = "暂无权限")
        else sendRedirect("/error/403")
    }
}
