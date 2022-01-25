package top.vuhe.admin.api.network

import com.fasterxml.jackson.databind.ObjectMapper
import top.vuhe.admin.api.constant.Browser
import top.vuhe.admin.api.constant.Header
import top.vuhe.admin.api.constant.JSON_UTF8
import top.vuhe.admin.api.constant.SystemType
import top.vuhe.admin.api.constant.UTF8
import top.vuhe.admin.api.spring.spring
import top.vuhe.admin.api.text.localeLowercase
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val objectMapper: ObjectMapper by spring()

/**
 * 此委托字段始终使用 getter 从 Spring 获取
 *
 * @return nullable [HttpServletRequest]
 */
fun requestContext(): Lazy<HttpServletRequest?> = NullableRequestEntrust

/**
 * 此委托字段始终使用 getter 从 Spring 获取
 *
 * @return nullable [HttpServletResponse]
 */
fun responseContext(): Lazy<HttpServletResponse?> = NullableResponseEntrust

/**
 * 获取未被包装的 [HttpServletRequest]
 */
val HttpServletRequest.orgRequest: HttpServletRequest
    get() = if (this is XssHttpServletRequest) request else this

/**
 * 判断是否为 Ajax 请求
 */
val HttpServletRequest.isAjax: Boolean
    get() {
        val requestType = getHeader(Header.X_REQUESTED_WITH)
        return Header.XML_HTTP_REQUEST == requestType
    }

/**
 * 写出 json 对象
 *
 * @param json json 字符串
 */
fun HttpServletResponse.writeJson(json: Any) {
    setHeader(Header.CONTENT_TYPE, JSON_UTF8)
    characterEncoding = UTF8
    writer.write(objectMapper.writeValueAsString(json))
}

/**
 * 获取查询参数，为空时无参数
 */
val HttpServletRequest.queryParam: String
    get() = queryString ?: ""

/**
 * 获取 UserAgent，为空时不存在
 */
val HttpServletRequest.userAgent: String
    get() = getHeader(Header.UA) ?: ""

/**
 * 获取浏览器类型
 */
val HttpServletRequest.browser: String
    get() = when {
        userAgent.contains(Browser.FIRE_FOX_UA) -> Browser.FIRE_FOX_NAME
        userAgent.contains(Browser.CHROME_UA) -> Browser.CHROME_NAME
        userAgent.contains(Browser.IE_UA) -> Browser.IE_NAME
        userAgent.contains(Browser.SAFARI_UA) -> Browser.SAFARI_NAME
        else -> Browser.UNKNOWN
    }

/**
 * 获取浏览器类型
 */
val HttpServletRequest.systemType: String
    get() = when {
        userAgent.localeLowercase().contains(SystemType.WIN_UA) -> SystemType.WIN_NAME
        userAgent.localeLowercase().contains(SystemType.MAC_UA) -> SystemType.MAC_NAME
        userAgent.localeLowercase().contains(SystemType.UNIX_UA) -> SystemType.UNIX_NAME
        userAgent.localeLowercase().contains(SystemType.ANDROID_UA) -> SystemType.ANDROID_NAME
        userAgent.localeLowercase().contains(SystemType.IPHONE_UA) -> SystemType.IPHONE_NAME
        else -> SystemType.UNKNOWN + userAgent
    }
