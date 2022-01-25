package top.vuhe.admin.api.network

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Safelist
import top.vuhe.admin.api.constant.EMPTY
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

    override fun getParameter(name: String): String {
        var name0 = name
        if (("content" == name0 || name0.endsWith("WithHtml")) && !isIncludeRichText) {
            return super.getParameter(name0)
        }
        name0 = clean(name0)
        var value = super.getParameter(name0)
        if (value.isNotBlank()) {
            value = clean(value)
        }
        return value
    }

    override fun getParameterValues(name: String): Array<String> {
        val arr = super.getParameterValues(name)
        if (arr != null) {
            for (i in arr.indices) {
                arr[i] = clean(arr[i])
            }
        }
        return arr
    }

    override fun getHeader(name: String): String {
        var name0 = name
        name0 = clean(name0)
        var value = super.getHeader(name0)
        if (value.isNotBlank()) {
            value = clean(value)
        }
        return value
    }

    companion object {
        private val safeList = Safelist.basicWithImages().apply {
            addAttributes(":all", "style")
        }

        private val outputSettings = Document.OutputSettings().prettyPrint(false)

        private fun clean(content: String): String {
            return Jsoup.clean(content, EMPTY, safeList, outputSettings)
        }
    }
}
