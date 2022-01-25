package top.vuhe.admin.spring.web.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。
 * 有效时间为10秒内。
 *
 * @author vuhe
 */
@Component
class SameUrlDataInterceptor @Autowired constructor(
    objectMapper: ObjectMapper
) : RepeatSubmitInterceptor(objectMapper) {
    override fun isRepeatSubmit(request: HttpServletRequest): Boolean {
        // 本次参数及系统时间
        val nowParams: String = objectMapper.writeValueAsString(request.parameterMap)
        val repeatFlag = SubmitRepeatFlag(nowParams, System.currentTimeMillis())

        val session = request.session

        @Suppress("UNCHECKED_CAST")
        val sessionObj = session.getAttribute(SESSION_REPEAT_KEY) as HashMap<String, SubmitRepeatFlag>?
            ?: HashMap()

        // 请求地址
        val url = request.requestURI
        // 判断是否为重复提交，判断见类的 equals
        if (sessionObj[url] == repeatFlag) return true

        // 非重复提交 保存参数信息
        sessionObj[url] = repeatFlag
        session.setAttribute(SESSION_REPEAT_KEY, sessionObj)
        return false
    }

    /**
     * 用于判断参数是否相同
     *
     * 仅作为 value 存储，不使用 hash
     */
    private class SubmitRepeatFlag(
        private val repeatParams: String,
        private val repeatTime: Long
    ) {
        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            return if (other is SubmitRepeatFlag) {
                // 两次 url、参数一致，且间隔时间小于设定，为一致
                repeatParams == other.repeatParams &&
                        repeatTime - other.repeatTime < intervalTime * 1000L
            } else false
        }

        override fun hashCode(): Int = repeatParams.hashCode()
    }

    companion object {
        /**
         * 间隔时间，单位:秒 默认10秒
         *
         * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
         */
        private var intervalTime = 10

        private const val SESSION_REPEAT_KEY = "repeatData"
    }
}
