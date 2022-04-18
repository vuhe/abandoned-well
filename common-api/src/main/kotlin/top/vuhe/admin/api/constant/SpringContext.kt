package top.vuhe.admin.api.constant

import cn.hutool.extra.spring.SpringUtil
import kotlin.reflect.KClass

/**
 * 通过委托，从 spring 容器中获取 bean
 * ```kotlin
 * object Test {
 *     val bean: Bean by spring()
 * }
 * ```
 */
inline fun <reified T : Any> spring() = spring(T::class)

@PublishedApi
internal fun <T : Any> spring(clazz: KClass<T>) = lazy { SpringUtil.getBean(clazz.java) }
