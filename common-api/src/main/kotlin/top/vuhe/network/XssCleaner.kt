package top.vuhe.network

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.slf4j.LoggerFactory
import top.vuhe.extra.xssClean
import java.beans.PropertyEditorSupport

object XssCleaner {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val tl = ThreadLocal<Boolean>()

    /** 用于保存此请求(线程)是否需要过滤 */
    var enable: Boolean
        get() = tl.get() ?: false
        set(value) = tl.set(value)

    fun clear() {
        tl.remove()
    }

    val form = object : PropertyEditorSupport() {
        override fun getAsText(): String = value?.toString() ?: ""
        override fun setAsText(text: String) {
            value = if (enable) {
                xssClean(text).also {
                    log.debug("Request value:{} cleaned, current value is:{}.", text, it)
                }
            } else text.trim()
        }
    }

    val json = object : JsonDeserializer<String>() {
        override fun deserialize(p: JsonParser, ctx: DeserializationContext): String? {
            // XSS filter
            val text = p.valueAsString ?: return null
            return if (enable) {
                xssClean(text).also {
                    log.debug("Json value:{} cleaned, current value is:{}.", text, it)
                }
            } else {
                text.trim()
            }
        }
    }
}
