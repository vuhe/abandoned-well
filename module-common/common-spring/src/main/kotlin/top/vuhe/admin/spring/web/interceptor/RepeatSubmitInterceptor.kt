package top.vuhe.admin.spring.web.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import top.vuhe.admin.spring.web.annotation.RepeatSubmit
import top.vuhe.admin.spring.web.response.ResultObj
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * ### 防止重复提交拦截器
 *
 * @author vuhe
 */
abstract class RepeatSubmitInterceptor(
    protected val objectMapper: ObjectMapper
) : HandlerInterceptor {
    /**
     * 前置拦截,进入处理活力前判断当前提交的内容是否重复
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return if (handler is HandlerMethod) {
            val method = handler.method
            val annotation = method.getAnnotation(RepeatSubmit::class.java)
            if (annotation != null && isRepeatSubmit(request)) {
                val result = ResultObj.Fail<Nothing>(message = "不允许重复提交，请稍后再试")
                response.setHeader("Content-type", "application/json;charset=UTF-8")
                response.characterEncoding = "UTF-8"
                response.writer.write(objectMapper.writeValueAsString(result))
                false
            } else true
        } else {
            super.preHandle(request, response, handler)
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     */
    abstract fun isRepeatSubmit(request: HttpServletRequest): Boolean
}
