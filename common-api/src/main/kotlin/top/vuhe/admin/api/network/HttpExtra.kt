package top.vuhe.admin.api.network

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.properties.ReadOnlyProperty

private const val charset = "UTF-8"

/**
 * 此委托字段始终使用 getter 从 Spring 获取
 *
 * @return [HttpServletRequest]
 */
fun requestContext(): ReadOnlyProperty<Any?, HttpServletRequest> = ThreadServletRequest

/**
 * 判断是否为 Ajax 请求
 */
val HttpServletRequest.isAjax: Boolean
    get() = "XMLHttpRequest" == getHeader("X-Requested-With")

/**
 * 获取 UserAgent
 */
val HttpServletRequest.userAgent: HttpUserAgent
    get() = HttpUserAgent(getHeader("User-Agent"))

fun HttpServletResponse.setContent(type: ContentType) {
    contentType = type.value
    characterEncoding = charset
}
