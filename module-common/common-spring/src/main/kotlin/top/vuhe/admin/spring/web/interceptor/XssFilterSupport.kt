package top.vuhe.admin.spring.web.interceptor

import top.vuhe.admin.api.network.XssHttpServletRequest
import java.util.regex.Pattern
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class XssFilterSupport : Filter {
    private var isIncludeRichText = false
    private var excludes: List<String> = emptyList()

    override fun init(filterConfig: FilterConfig) {
        val isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText") ?: ""
        if (isIncludeRichText.isNotBlank()) {
            this.isIncludeRichText = isIncludeRichText.toBoolean()
        }
        val temp = filterConfig.getInitParameter("excludes")
        if (temp != null) {
            excludes = temp.split(",")
        }
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse?, filterChain: FilterChain) {
        val req = request as HttpServletRequest
        val resp = response as HttpServletResponse?
        if (handleExcludeURL(req)) {
            filterChain.doFilter(request, resp)
            return
        }
        val xssRequest = XssHttpServletRequest(request, isIncludeRichText)
        filterChain.doFilter(xssRequest, resp)
    }

    private fun handleExcludeURL(request: HttpServletRequest): Boolean {
        if (excludes.isEmpty()) {
            return false
        }
        val url = request.servletPath
        return excludes.fold(false) { left, it ->
            left || Pattern.compile("^$it").matcher(url).find()
        }
    }
}
