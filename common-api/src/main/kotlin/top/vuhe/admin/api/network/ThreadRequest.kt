package top.vuhe.admin.api.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** 此委托字段始终使用 getter 从 Spring 获取 */
fun requestContext(): ReadOnlyProperty<Any?, HttpServletRequest> = ThreadServletRequest

/**
 * 从 Spring Web 的 RequestContext 委托获取 request
 *
 * @author vuhe
 */
private object ThreadServletRequest : ReadOnlyProperty<Any?, HttpServletRequest> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): HttpServletRequest {
        val request = kotlin.runCatching {
            (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
        }.getOrNull()
        return requireNotNull(request) { "内部错误(request is null)" }
    }
}
