package top.vuhe.admin.spring.security.session

import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import top.vuhe.admin.api.network.requestContext
import top.vuhe.admin.spring.web.session.HttpSessionCenter
import javax.servlet.http.HttpSession


/**
 * security session 注册中心
 *
 * @author vuhe
 */
object SecuritySessionRegistry : SessionRegistry by SessionRegistryImpl() {
    private val request by requestContext()

    fun expiredSession(session: HttpSession) {
        // 此线程如果无登录用户信息，续期无效
        val currUserId = request?.session?.getAttribute("currentUser")
                as String? ?: return
        val sessionId = session.id

        // 从sessionRegistry中获取和当前用户名相同的信息
        val userId = allPrincipals.find {
            it as String? == currUserId
        } as String?

        // 查询用户所有非当前 session 的 SessionInformation，包括过期 session
        val sessionInformationList = getAllSessions(userId, true)
            .filterNot { sessionId == it.sessionId }

        sessionInformationList.forEach { sessionInformation ->
            // 从sessionRegistry中清除session信息
            sessionInformation.expireNow()
            removeSessionInformation(sessionInformation.sessionId)
            // 从HttpSessionContext中清除session信息
            HttpSessionCenter.remove(sessionInformation.sessionId)
        }
    }
}
