package top.vuhe.admin.spring.security.logout

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import top.vuhe.admin.spring.web.HttpServletResponseHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 自定义用户注销处理类
 *
 * @author vuhe
 */
class SecureLogoutHandler(objectMapper: ObjectMapper) :
    HttpServletResponseHandler(objectMapper), LogoutHandler, LogoutSuccessHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        require(request != null) { "HttpServletRequest required" }
        // 销毁session
        request.getSession(false)?.let {
            log.debug("Invalidating session: " + it.id)
            it.invalidate()
        }
        // 清空Authentication
        SecurityContextHolder.getContext().authentication = null
        SecurityContextHolder.clearContext()
    }

    override fun onLogoutSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication?
    ) {
        SecurityContextHolder.clearContext()
        httpServletResponse.success(message = "注销成功")
    }
}
