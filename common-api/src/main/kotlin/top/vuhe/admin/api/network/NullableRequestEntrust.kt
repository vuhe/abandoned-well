package top.vuhe.admin.api.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 从 Spring Web 的 RequestContext
 * 委托获取 request
 *
 * @author vuhe
 */
internal object NullableRequestEntrust : ReadOnlyProperty<Any, HttpServletRequest?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): HttpServletRequest? {
        return try {
            (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
        } catch (e: Exception) {
            null
        }
    }
}
