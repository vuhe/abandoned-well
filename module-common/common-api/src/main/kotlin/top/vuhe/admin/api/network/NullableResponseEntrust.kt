package top.vuhe.admin.api.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 从 Spring Web 的 ResponseContext
 * 委托获取 response
 *
 * @author vuhe
 */
internal object NullableResponseEntrust : ReadOnlyProperty<Any, HttpServletResponse?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): HttpServletResponse? {
        return try {
            (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.response
        } catch (e: Exception) {
            null
        }
    }
}
