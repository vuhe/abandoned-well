package top.vuhe.admin.spring.security.session

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.security.core.session.SessionInformation
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.session.SessionInformationExpiredEvent
import org.springframework.security.web.session.SessionInformationExpiredStrategy
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.spring.web.session.HttpSessionCenter
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Security 在线用户监测、踢人下线
 *
 * @author vuhe
 */
class SessionExpiredHandler(
    private val userDetailsService: UserSecurityService
) : CommandLineRunner, SessionInformationExpiredStrategy {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()

    /**
     * 定时检测
     */
    override fun run(vararg args: String) = SessionRemoveExecutor {
        // 从sessionRegistry中获取所有的用户信息
        SecuritySessionRegistry.allPrincipals.forEach {
            val userId = it as String
            val user = userDetailsService.getLoginUserById(userId)
            if (!user.isLoginNonExpired) {
                val sessionInformationList: List<SessionInformation> =
                    SecuritySessionRegistry.getAllSessions(userId, false)
                sessionInformationList.forEach { sessionInformation ->
                    // 清除已经过期的session（SessionRegistry）
                    sessionInformation.expireNow()
                    SecuritySessionRegistry.removeSessionInformation(sessionInformation.sessionId)
                    log.info(
                        "HttpSessionId[${sessionInformation.sessionId}]------>已从SessionRegistry中移除"
                    )
                    // 销毁已经过期的session
                    HttpSessionCenter[sessionInformation.sessionId]?.invalidate()
                    log.info(
                        "HttpSessionId[${sessionInformation.sessionId}]------>已从HttpSessionContext中移除"
                    )
                }
            } else {
                log.info("[在线用户检测] 目前sessionRegistry中，没有已经过期的httpSession")

            }
        }
    }

    /**
     * session 过期跳转
     */
    override fun onExpiredSessionDetected(event: SessionInformationExpiredEvent) {
        redirectStrategy.sendRedirect(event.request, event.response, "/login?abnormalout=1")
    }

    /**
     * 定期执行清除 session 工作
     */
    private object SessionRemoveExecutor {
        private val threadPool = ScheduledThreadPoolExecutor(1) { r ->
            Thread(r).apply {
                name = "removeSession"
                isDaemon = true
            }
        }

        operator fun invoke(run: () -> Unit) {
            threadPool.scheduleWithFixedDelay(run, 60, 10, TimeUnit.SECONDS)
        }
    }
}
