package top.vuhe.admin.spring.security

import org.springframework.security.core.context.SecurityContextHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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
