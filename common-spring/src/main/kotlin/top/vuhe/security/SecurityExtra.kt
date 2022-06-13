package top.vuhe.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.HttpSecurityDsl
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** [HttpSecurity] 的 dsl 配置，适配 spring boot 2.7+ */
fun HttpSecurity.config(block: HttpSecurityDsl.() -> Unit): SecurityFilterChain =
    also { it.invoke(block) }.build()

fun securityContext(): ReadOnlyProperty<Any?, String> = SecurityUserId

private object SecurityUserId : ReadOnlyProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = authentication?.principal as? String?
        return checkNotNull(userId) {
            "Thread error, userId does not exist in this thread, please check the parameters!"
        }
    }
}
