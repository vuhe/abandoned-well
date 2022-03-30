package top.vuhe.admin.api.cache.scope

import top.vuhe.admin.api.cache.map.TimeoutCacheMap
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * 带有过期时间的缓存
 *
 * @author vuhe
 */
sealed class TimeoutCacheScope {
    private val caches = ConcurrentHashMap<String, TimeoutCacheMap<*>>()
    private val defaultTimeout = 10.minutes

    /**
     * 创建一个新缓存
     */
    protected abstract fun <V : Any> createCache(name: String, timeout: Duration): TimeoutCacheMap<V>

    /**
     * 获取一个缓存，如果没有则新建
     */
    operator fun <V : Any> get(cache: String): TimeoutCacheMap<V> = get(cache, defaultTimeout)

    /**
     * 获取一个缓存并指定过期时间，如果没有则新建
     */
    operator fun <V : Any> get(cache: String, timeout: Duration): TimeoutCacheMap<V> {
        @Suppress("UNCHECKED_CAST")
        val timeoutCache = caches[cache] as? TimeoutCacheMap<V>
        return timeoutCache ?: createCache(cache, timeout)
    }
}
