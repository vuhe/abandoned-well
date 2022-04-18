package top.vuhe.admin.spring.web.interceptor

import top.vuhe.admin.api.network.XssHttpServletRequest
import java.util.regex.Pattern
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

class XssFilterSupport : Filter {
    private var isIncludeRichText = false
    private var excludes: List<Pattern> = emptyList()

    override fun init(filterConfig: FilterConfig) {
        val isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText") ?: ""
        if (isIncludeRichText.isNotBlank()) {
            this.isIncludeRichText = isIncludeRichText.toBoolean()
        }
        val temp = filterConfig.getInitParameter("excludes")
        if (temp != null) {
            excludes = temp.split(",").map { Pattern.compile("^$it") }
        }
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse?, filterChain: FilterChain) {
        val handledReq = if (request is HttpServletRequest) {
            if (handleExcludeURL(request)) request
            else XssHttpServletRequest(request, isIncludeRichText)
        } else request

        filterChain.doFilter(handledReq, response)
    }

    private fun handleExcludeURL(request: HttpServletRequest): Boolean {
        if (excludes.isEmpty()) {
            return false
        }
        val url = request.servletPath
        return excludes.fold(false) { left, it ->
            left || it.matcher(url).find()
        }
    }
}
