package top.vuhe.admin.api.network

import cn.hutool.http.useragent.UserAgent
import cn.hutool.http.useragent.UserAgentUtil
import javax.servlet.http.HttpServletRequest
import kotlin.properties.ReadOnlyProperty

/**
 * 此委托字段始终使用 getter 从 Spring 获取
 *
 * @return nullable [HttpServletRequest]
 */
fun requestContext(): ReadOnlyProperty<Any?, HttpServletRequest?> = NullableRequestEntrust

private val HttpServletRequest.xRequestedWith: String
    get() = getHeader("X-Requested-With") ?: ""

/**
 * 判断是否为 Ajax 请求
 */
val HttpServletRequest.isAjax: Boolean
    get() = "XMLHttpRequest" == xRequestedWith

/**
 * 获取查询参数，为空时无参数
 */
val HttpServletRequest.queryParam: String
    get() = queryString ?: ""

private val HttpServletRequest.userAgent: UserAgent
    get() = UserAgentUtil.parse(getHeader("User-Agent") ?: "")

/**
 * 获取浏览器类型
 */
val HttpServletRequest.browser: String
    get() = userAgent.browser.name

/**
 * 获取浏览器类型
 */
val HttpServletRequest.systemType: String
    get() = userAgent.platform.name
