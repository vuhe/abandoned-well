package top.vuhe.admin.api.network

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Safelist
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

        val name0 = name?.let { clean(it) }
        return super.getParameter(name0)?.let { clean(it) }
    }

    override fun getParameterValues(name: String?): Array<String?>? {
        return super.getParameterValues(name)?.also {
            it.forEachIndexed { i, str -> it[i] = str?.let { s -> clean(s) } }
        }
    }

    override fun getHeader(name: String?): String? {
        val name0 = name?.let { clean(it) }
        return super.getHeader(name0)?.let { clean(it) }
    }

    companion object {
        private val safeList = Safelist.basicWithImages().apply {
            addAttributes(":all", "style")
        }

        private val outputSettings = Document.OutputSettings().prettyPrint(false)

        private fun clean(content: String): String {
            return if (content.isBlank()) content
            else Jsoup.clean(content, "", safeList, outputSettings)
        }
    }
}
