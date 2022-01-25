package top.vuhe.admin.api.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse

/**
 * 从 Spring Web 的 ResponseContext
 * 委托获取 response
 *
 * @author vuhe
 */
internal object NullableResponseEntrust : Lazy<HttpServletResponse?> {
    override val value: HttpServletResponse?
        get() = try {
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.response
        } catch (e: Exception) {
            null
        }

    /**
     * 从 Spring 获取，默认已经初始化
     */
    override fun isInitialized(): Boolean = true
}
