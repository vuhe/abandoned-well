package top.vuhe.admin.spring.web.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import top.vuhe.admin.spring.web.annotation.RepeatSubmit
import top.vuhe.admin.spring.web.response.fail
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * ### 防止重复提交拦截器
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。
 * 有效时间为10秒内。
 *
 * @author vuhe
 */
@Component
class RepeatSubmitInterceptor(
    private val objectMapper: ObjectMapper
) : HandlerInterceptor {
    /**
     * 前置拦截,进入处理活力前判断当前提交的内容是否重复
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return if (handler is HandlerMethod) {
            val method = handler.method
            val annotation = method.getAnnotation(RepeatSubmit::class.java)
            if (annotation != null && isRepeatSubmit(request)) {
                response.fail(message = "不允许重复提交，请稍后再试")
                false
            } else true
        } else {
            super.preHandle(request, response, handler)
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     */
    private fun isRepeatSubmit(request: HttpServletRequest): Boolean {
        // 本次参数及系统时间
        val nowParams: String = objectMapper.writeValueAsString(request.parameterMap)
        val repeatFlag = SubmitRepeatFlag(nowParams, System.currentTimeMillis())

        val session = request.session

        val repeatMap = session.repeatData

        // 请求地址
        val url = request.requestURI
        // 判断是否为重复提交，判断见类的 equals
        if (repeatMap[url] == repeatFlag) return true

        // 非重复提交 保存参数信息
        repeatMap[url] = repeatFlag
        session.repeatData = repeatMap
        return false
    }

    @Suppress("UNCHECKED_CAST")
    private var HttpSession.repeatData: HashMap<String, SubmitRepeatFlag>
        get() = getAttribute("repeatData") as? HashMap<String, SubmitRepeatFlag> ?: HashMap()
        set(value) = setAttribute("repeatData", value)

    /**
     * 用于判断参数是否相同
     *
     * 仅作为 value 存储，不使用 hash
     */
    private class SubmitRepeatFlag(
        private val repeatParams: String, private val repeatTime: Long
    ) {
        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            return if (other is SubmitRepeatFlag) {
                // 两次 url、参数一致，且间隔时间小于设定，为一致
                // 间隔时间 默认10秒
                repeatParams == other.repeatParams &&
                        repeatTime - other.repeatTime < 10 * 1000L
            } else false
        }

        override fun hashCode(): Int = repeatParams.hashCode()
    }
}
