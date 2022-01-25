package top.vuhe.admin.api.spring

import cn.hutool.extra.spring.SpringUtil
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

/**
 * spring 容器类，便于获取容器中的实例
 *
 * @author vuhe
 */
object SpringContainer {
    val applicationContext: ApplicationContext = SpringUtil.getApplicationContext()

    /**
     * 从 spring 容器中获取 bean
     *
     * @return bean 对象，在出现异常或不存在时返回 null
     */
    @Deprecated("使用 by spring()")
    operator fun <T : Any> get(clazz: KClass<T>): T? {
        return try {
            SpringUtil.getBean(clazz.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 从 spring 容器中获取 bean
     *
     * @return bean 对象，在出现异常或不存在时返回 null
     */
    infix fun <T : Any> getByClassName(clazzName: String): T? {
        return try {
            @Suppress("UNCHECKED_CAST")
            SpringUtil.getBean(Class.forName(clazzName)) as T?
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param T     Bean类型
     * @param clazz 类、接口，null表示获取所有bean
     * @return 类型对应的 bean，<key：bean 注册的 name, value：Bean>
     */
    infix fun <T : Any> getBeansOfType(clazz: KClass<T>): Map<String, T> =
        SpringUtil.getBeansOfType(clazz.java) ?: emptyMap()
}
