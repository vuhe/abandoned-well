package top.vuhe.network

import eu.bitwalker.useragentutils.UserAgent

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
