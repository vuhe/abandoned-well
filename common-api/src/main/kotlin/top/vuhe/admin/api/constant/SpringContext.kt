package top.vuhe.admin.api.constant

import cn.hutool.extra.spring.SpringUtil
import kotlin.reflect.KClass

inline fun <reified T : Any> spring() = spring(T::class)

fun <T : Any> spring(clazz: KClass<T>) = lazy { SpringUtil.getBean(clazz.java) }
