package top.vuhe.admin.api.network

import cn.hutool.http.HtmlUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * 对 xss 攻击处理的包装
 *
 * @author vuhe
 */
class XssHttpServletRequest(
    val request: HttpServletRequest,
    private val isIncludeRichText: Boolean
) : HttpServletRequestWrapper(request) {

    override fun getParameter(name: String?): String? {
        if (("content" == name || name?.endsWith("WithHtml") == true)
            && !isIncludeRichText
        ) return super.getParameter(name)

        return super.getParameter(name.cleanText()).cleanText()
    }

    override fun getParameterValues(name: String?): Array<String?>? {
        return super.getParameterValues(name)?.also {
            it.forEachIndexed { i, str -> it[i] = str.cleanText() }
        }
    }

    override fun getHeader(name: String?): String? {
        return super.getHeader(name.cleanText()).cleanText()
    }

    private fun String?.cleanText(): String? {
        return if (isNullOrBlank()) this
        else HtmlUtil.filter(this)
    }
}
