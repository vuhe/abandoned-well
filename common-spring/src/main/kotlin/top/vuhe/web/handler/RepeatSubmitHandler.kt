package top.vuhe.web.handler

import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import top.vuhe.annotation.RepeatSubmit
import top.vuhe.web.HttpServletResponseHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * ## 防止重复提交拦截器
 * > 本拦截器是基于内存设计，未考虑(反)序列化带来的问题
 *
 * 判断请求url和数据是否和上一次相同， 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 *
 * @author vuhe
 */
object RepeatSubmitHandler : HttpServletResponseHandler(), HandlerInterceptor {
    /** 前置拦截,进入处理活力前判断当前提交的内容是否重复 */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return if (handler is HandlerMethod) {
            val annotation = handler.method.getAnnotation(RepeatSubmit::class.java)
            if (annotation != null && request.isRepeatSubmit()) {
                response.fail(message = "重复提交过快，请稍等10秒后再试")
                false
            } else true
        } else {
            super.preHandle(request, response, handler)
        }
    }

    /** 验证是否重复提交由子类实现具体的防重复提交的规则 */
    private fun HttpServletRequest.isRepeatSubmit(): Boolean {
        // 本次参数及系统时间
        val nowParams: String = objectMapper.writeValueAsString(parameterMap)
        val repeatFlag = SubmitRepeatFlag(nowParams)
        val session = session
        val repeatMap = session.repeatData

        // 请求地址
        val url = requestURI
        // 判断是否为重复提交，判断见类的 equals
        if (repeatFlag similar repeatMap[url]) return true

        // 非重复提交 保存参数信息
        repeatMap[url] = repeatFlag
        session.repeatData = repeatMap
        return false
    }

    @Suppress("UNCHECKED_CAST")
    private var HttpSession.repeatData: HashMap<String, SubmitRepeatFlag>
        get() = getAttribute("repeatData") as? HashMap<String, SubmitRepeatFlag> ?: HashMap()
        set(value) = setAttribute("repeatData", value)

    private class SubmitRepeatFlag(private val repeatParams: String) {
        private val repeatTime: Duration = System.currentTimeMillis().milliseconds

        infix fun similar(other: SubmitRepeatFlag?): Boolean {
            if (other == null) return false
            // 两次 url、参数一致，且间隔时间小于设定，为一致
            return repeatParams == other.repeatParams && timeSimilar(repeatTime, other.repeatTime)
        }

        private fun timeSimilar(a: Duration, b: Duration): Boolean {
            val result = (a - b).let { if (it > ZERO) it else -it }
            // 间隔时间 默认10秒
            return result < 10.seconds
        }
    }
}
