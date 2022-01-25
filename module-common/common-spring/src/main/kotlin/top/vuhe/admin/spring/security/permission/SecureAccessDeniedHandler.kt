package top.vuhe.admin.spring.security.permission

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import top.vuhe.admin.api.network.isAjax
import top.vuhe.admin.api.network.writeJson
import top.vuhe.admin.spring.web.response.ResultObj
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 自定义 Security 权限处理类
 * - 用户无权限
 * - 用户未登录
 *
 * @author vuhe
 */
object SecureAccessDeniedHandler : AccessDeniedHandler, AuthenticationEntryPoint {
    override fun handle(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        if (httpServletRequest.isAjax) {
            val result = ResultObj.Fail<Nothing>(code = 403, message = "暂无权限")
            httpServletResponse.writeJson(result)
        } else {
            httpServletResponse.sendRedirect("/error/403")
        }
    }

    override fun commence(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException?
    ) {
        val result = ResultObj.Fail<Nothing>(code = 401, message = "未知账户")
        httpServletResponse.writeJson(result)
    }
}
