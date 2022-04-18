package top.vuhe.admin.api.network

import cn.hutool.http.useragent.UserAgentUtil

class HttpUserAgent internal constructor(agent: String) {
    private val userAgent = UserAgentUtil.parse(agent)
    val browser: String get() = userAgent.browser.name
    val system: String get() = userAgent.platform.name
}
