package top.vuhe.admin.api.network

import cn.hutool.http.useragent.UserAgent
import cn.hutool.http.useragent.UserAgentUtil

class HttpUserAgent internal constructor(agent: String?) {
    private val userAgent: UserAgent? = UserAgentUtil.parse(agent)
    val browser: String get() = userAgent?.browser?.name ?: "Unknown"
    val system: String get() = userAgent?.platform?.name ?: "Unknown"
}
