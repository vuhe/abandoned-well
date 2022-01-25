package top.vuhe.admin.api.spring

import kotlin.reflect.KClass

/**
 * 使用委托，从 Spring 中获取 Bean
 */
inline fun <reified T : Any> spring(): Lazy<T> = spring(T::class)

/**
 * 使用委托，从 Spring 中获取 Bean
 *
 * @param clazz 获取对象的 Class
 */
fun <T : Any> spring(clazz: KClass<T>): Lazy<T> {
    return SpringLazy(clazz)
}
