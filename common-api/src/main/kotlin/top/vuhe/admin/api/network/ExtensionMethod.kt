package top.vuhe.admin.api.network

import cn.hutool.extra.spring.SpringUtil
import cn.hutool.http.useragent.UserAgent
import cn.hutool.http.useragent.UserAgentUtil
import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.properties.ReadOnlyProperty

private val objectMapper: ObjectMapper by lazy { SpringUtil.getBean(ObjectMapper::class.java) }

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
 * 写出 json 对象
 *
 * @param json json 字符串
 */
fun HttpServletResponse.writeJson(json: Any) {
    contentType = "application/json;charset=UTF-8"
    characterEncoding = Charsets.UTF_8.name()
    writer.write(objectMapper.writeValueAsString(json))
}

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
