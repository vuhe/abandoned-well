package top.vuhe.admin.api.cache.group

import org.springframework.cache.Cache
import org.springframework.cache.Cache.ValueRetrievalException
import java.time.Duration
import java.util.concurrent.Callable

/**
 * 有过期时间的缓存
 *
 * @param cacheName       缓存名称
 * @param allowNullValues 是否允许 null 值
 * @param timeout         过期时间
 * @author vuhe
 */
abstract class TimeoutCache(
    protected val cacheName: String,
    private val allowNullValues: Boolean,
    protected val timeout: Duration
) : Cache {
    override fun getName(): String = cacheName

    /**
     * 容器内所有条目的迭代器
     */
    protected abstract fun iterator(): Iterator<Pair<Any, Any?>>

    override fun getNativeCache(): Sequence<Pair<Any, Any?>> {
        val iterator = iterator()
        return object : Sequence<Pair<Any, Any?>> {
            override fun iterator(): Iterator<Pair<Any, Any?>> = iterator
        }
    }

    /**
     * 获取未包装对象
     */
    protected abstract fun getValue(key: Any): Any?

    override fun get(key: Any): Cache.ValueWrapper? {
        val value = getValue(key) ?: return null
        return if (allowNullValues && value is String && value == nullValue) {
            CacheValue(null)
        } else CacheValue(value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> get(key: Any, type: Class<T>?): T? {
        val value = getValue(key)
        if (value != null && type != null && !type.isInstance(value)) {
            throw IllegalStateException("Cached value is not of required type [${type.name}]: $value")
        }
        return value as T?
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> get(key: Any, valueLoader: Callable<T>): T? {
        val value = getValue(key)
        return if (value == null) {
            val newValue = try {
                valueLoader.call()
            } catch (e: Exception) {
                throw ValueRetrievalException(key, valueLoader, e)
            }
            put(key, newValue)
            newValue
        } else value as T?
    }

    /**
     * 原生 put 方法，此方法无需处理 allowNullValues
     */
    protected abstract fun putValue(key: Any, value: Any?)

    override fun put(key: Any, value: Any?) {
        if (allowNullValues && value == null) {
            putValue(key, nullValue)
        } else if (value != null) {
            putValue(key, value)
        }
    }

    /**
     * 移除对象方法
     *
     * @return 是否存在
     */
    protected abstract fun removeIfPresent(key: Any): Boolean

    override fun evict(key: Any) {
        evictIfPresent(key)
    }

    override fun evictIfPresent(key: Any): Boolean {
        return removeIfPresent(key)
    }

    /**
     * 清除容器方法
     *
     * @return 是否存在
     */
    protected abstract fun clearIfNotEmpty(): Boolean

    override fun clear() {
        invalidate()
    }

    override fun invalidate(): Boolean {
        return clearIfNotEmpty()
    }

    /**
     * cache value 的包装
     */
    private class CacheValue(private val value: Any?) : Cache.ValueWrapper {
        override fun get(): Any? = value
    }

    companion object {
        private const val nullValue = "null-value"
    }
}
