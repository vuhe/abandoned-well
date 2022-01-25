package top.vuhe.admin.spring.web.session

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.annotation.WebListener
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

/**
 * http session 的集中处理
 *
 * @author vuhe
 */
@Component
@WebListener
object HttpSessionCenter : HttpSessionListener {
    private val sessionMap: MutableMap<String, HttpSession> =
        ConcurrentHashMap<String, HttpSession>()

    @Value("\${server.servlet.session.timeout}")
    lateinit var timeout: Duration

    operator fun get(id: String): HttpSession? = sessionMap[id]

    fun remove(id: String) {
        sessionMap.remove(id)
    }

    override fun sessionCreated(se: HttpSessionEvent) {
        se.session?.let { session ->
            sessionMap[session.id] = session
        }
    }

    override fun sessionDestroyed(se: HttpSessionEvent) {
        val id = se.session?.id
        sessionMap.remove(id)
    }
}
