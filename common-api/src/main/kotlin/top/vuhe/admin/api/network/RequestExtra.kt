package top.vuhe.admin.api.network

import javax.servlet.http.HttpServletRequest
import kotlin.properties.ReadOnlyProperty

/**
 * 此委托字段始终使用 getter 从 Spring 获取
 *
 * @return nullable [HttpServletRequest]
 */
fun requestContext(): ReadOnlyProperty<Any?, HttpServletRequest> = ThreadServletRequest

private operator fun HttpServletRequest.get(head: HttpHeader): String {
    return getHeader(head.tag) ?: ""
}

/**
 * 判断是否为 Ajax 请求
 */
val HttpServletRequest.isAjax: Boolean
    get() = "XMLHttpRequest" == get(HttpHeader.XRequestedWith)

/**
 * 获取 UserAgent
 */
val HttpServletRequest.userAgent: HttpUserAgent
    get() = HttpUserAgent(get(HttpHeader.UserAgent))
