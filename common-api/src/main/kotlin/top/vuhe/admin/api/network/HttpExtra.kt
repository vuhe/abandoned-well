package top.vuhe.admin.api.network

import eu.bitwalker.useragentutils.UserAgent
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.properties.ReadOnlyProperty

/** 默认编码m */
private const val charset = "UTF-8"

/** 此委托字段始终使用 getter 从 Spring 获取 */
fun requestContext(): ReadOnlyProperty<Any?, HttpServletRequest> = ThreadServletRequest

/** 判断是否为 Ajax 请求 */
val HttpServletRequest.isAjax: Boolean
    get() = "XMLHttpRequest" == getHeader("X-Requested-With")

/** 获取 UserAgent */
val HttpServletRequest.userAgent: HttpUserAgent
    get() = HttpUserAgent(getHeader("User-Agent"))

/** 设置 ContentType 和 characterEncoding */
fun HttpServletResponse.setContent(type: ContentType) {
    contentType = type.value
    characterEncoding = charset
}

/** 设置 filename, 更改 Content-disposition */
fun HttpServletResponse.setFilename(name: String) {
    // 这里URLEncoder.encode可以防止中文乱码
    val encoded = URLEncoder.encode(name, "UTF-8").replace("\\+", "%20")
    setHeader("Content-disposition", "attachment;filename*=utf-8''$encoded")
}

/** user agent 信息 */
class HttpUserAgent internal constructor(agent: String?) {
    private val userAgent = UserAgent.parseUserAgentString(agent)
    val browser: String get() = userAgent.browser.getName()
    val system: String get() = userAgent.operatingSystem.getName()
}

/** ContentType 常量 */
@Suppress("SpellCheckingInspection")
enum class ContentType(val value: String) {
    FILE_DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    FILE_XLS("application/vnd.ms-excel"),
    TEXT_JSON("application/json;charset=UTF-8")
}
