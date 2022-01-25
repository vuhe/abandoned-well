package top.vuhe.admin.api.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

/**
 * 从 Spring Web 的 RequestContext
 * 委托获取 request
 *
 * @author vuhe
 */
internal object NullableRequestEntrust : Lazy<HttpServletRequest?> {
    override val value: HttpServletRequest?
        get() = try {
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
        } catch (e: Exception) {
            null
        }

    /**
     * 从 Spring 获取，默认已经初始化
     */
    override fun isInitialized(): Boolean = true
}
