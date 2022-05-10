package top.vuhe.admin.spring.web.handler

import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.AsyncHandlerInterceptor
import top.vuhe.admin.api.annotation.XssCleanIgnore
import top.vuhe.admin.api.network.XssCleaner
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object XssCleanHandler : AsyncHandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 1. 非控制器请求直接跳出
        if (handler !is HandlerMethod) {
            return true
        }
        // 2. 处理 XssIgnore 注解
        val xssCleanIgnore = handler.getMethodAnnotation(XssCleanIgnore::class.java)
        if (xssCleanIgnore == null) {
            XssCleaner.enable = true
        }
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        XssCleaner.clear()
    }

    override fun afterConcurrentHandlingStarted(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ) {
        XssCleaner.clear()
    }
}
