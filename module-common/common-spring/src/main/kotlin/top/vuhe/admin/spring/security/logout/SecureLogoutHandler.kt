package top.vuhe.admin.spring.security.logout

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.session.HttpSessionEventPublisher
import top.vuhe.admin.api.network.writeJson
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.spring.web.session.HttpSessionCenter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSessionEvent

/**
 * ### 自定义用户注销处理类
 *
 * @author vuhe
 */
class SecureLogoutHandler(
    private val httpSessionEventPublisher: HttpSessionEventPublisher
) : LogoutHandler, LogoutSuccessHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        require(request != null) { "HttpServletRequest required" }
        // 构建要销毁的session
        val sessionEvent = HttpSessionEvent(request.session)
        // 发布session销毁事件
        httpSessionEventPublisher.sessionDestroyed(sessionEvent)
        // 销毁session
        val session = request.getSession(false)
        // 移除HttpSessionContext中的session信息
        HttpSessionCenter.sessionDestroyed(sessionEvent)
        if (session != null) {
            log.debug("Invalidating session: " + session.id)
            session.invalidate()
        }
        // 清空Authentication
        val context = SecurityContextHolder.getContext()
        context.authentication = null

        SecurityContextHolder.clearContext()
    }

    override fun onLogoutSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication?
    ) {
        SecurityContextHolder.clearContext()
        val result = ResultObj.Success<Nothing>(code = 200, message = "注销成功")
        httpServletResponse.writeJson(result)
    }
}
