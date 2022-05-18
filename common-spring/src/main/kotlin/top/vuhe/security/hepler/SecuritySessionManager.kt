package top.vuhe.security.hepler

import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.security.web.session.SessionInformationExpiredEvent
import org.springframework.security.web.session.SessionInformationExpiredStrategy
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionIdListener
import javax.servlet.http.HttpSessionListener

/**
 * security session 注册中心
 *
 * @author vuhe
 */
object SecuritySessionManager :
    SessionRegistry by SessionRegistryImpl(), HttpSessionListener, HttpSessionIdListener,
    SessionInformationExpiredStrategy {
    private val redirectStrategy = DefaultRedirectStrategy()
    private val eventPublisher = HttpSessionEventPublisher()
    private val sessions = ConcurrentHashMap<String, HttpSession>()

    /**
     * 使一个用户的所有 session 失效
     */
    fun deleteSessionByUserId(userId: String) {
        getAllSessions(userId, false).asSequence()
            .mapNotNull { sessions[it.sessionId] }
            // 调用 session.invalidate()
            // 它在使 session 失效后，系统会调用 sessionDestroyed() 清理信息
            .forEach { kotlin.runCatching { it.invalidate() } }
    }

    /**
     * session 创建回调；
     * 此方法会缓存目前有效的 session
     */
    override fun sessionCreated(event: HttpSessionEvent) {
        eventPublisher.sessionCreated(event)
        // 在接收到 session 创建信息后，创建 sessions 缓存映射
        sessions[event.session.id] = event.session
    }

    /**
     * session 失效回调；
     * 此方法会在以下情况中回调：
     * - session 过期
     * - 主动调用 session.invalidate()
     *
     * 回调后，会清理 session 缓存和 SessionRegistry 缓存
     */
    override fun sessionDestroyed(event: HttpSessionEvent) {
        eventPublisher.sessionDestroyed(event)
        // 在接收到 session 注销信息后，清理 SessionRegistry 信息
        removeSessionInformation(event.session.id)
        // 在接收到 session 注销信息后，清理 sessions 缓存映射
        sessions.remove(event.session.id)
    }

    /**
     * session id 变更回调；
     * 此方法会更新 session 缓存
     */
    override fun sessionIdChanged(se: HttpSessionEvent, oldSessionId: String) {
        eventPublisher.sessionIdChanged(se, oldSessionId)
        // 在接收到 session 变更信息后，更新 sessions 缓存映射
        sessions[se.session.id] = se.session
        sessions.remove(oldSessionId)
    }

    /**
     * session 过期后的跳转
     */
    override fun onExpiredSessionDetected(event: SessionInformationExpiredEvent) {
        redirectStrategy.sendRedirect(event.request, event.response, "/login?fail=invalid")
    }
}
