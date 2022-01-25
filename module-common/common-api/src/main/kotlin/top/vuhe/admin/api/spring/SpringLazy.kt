package top.vuhe.admin.api.spring

import cn.hutool.extra.spring.SpringUtil
import kotlin.reflect.KClass

/**
 * 用于从 Spring 容器中延迟获取 Bean
 *
 * @author vuhe
 */
internal class SpringLazy<out T : Any>(
    private val clazz: KClass<T>
) : Lazy<T> {
    @Volatile
    private var _value: Any = UninitValue

    override val value: T
        get() {
            val v1 = _value
            @Suppress("UNCHECKED_CAST")
            when {
                v1 === NotFoundBean -> throw RuntimeException(" [by lazy] 未从 Spring 中找到对应 Bean！")
                v1 !== UninitValue -> return v1 as T
            }

            return synchronized(this) {
                val v2 = _value
                if (v2 !== UninitValue) {
                    @Suppress("UNCHECKED_CAST") (v2 as T)
                } else {
                    val typedValue = try {
                        SpringUtil.getBean(clazz.java)
                    } catch (e: Exception) {
                        null
                    }
                    if (typedValue == null) {
                        _value = NotFoundBean
                        throw RuntimeException(" [by lazy] 未从 Spring 中找到对应 Bean！")
                    } else {
                        _value = typedValue
                        typedValue
                    }
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UninitValue

    override fun toString(): String = if (isInitialized()) value.toString()
    else "还未从 Spring 容器中获取 Bean"

    companion object {
        private object UninitValue
        private object NotFoundBean
    }
}
