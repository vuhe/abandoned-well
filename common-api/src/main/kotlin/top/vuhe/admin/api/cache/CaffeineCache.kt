package top.vuhe.admin.api.cache

import com.github.benmanes.caffeine.cache.Caffeine
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

/**
 * ## 缓存的 Caffeine 实现
 *
 * 此缓存对象使用 Caffeine 实现
 */
internal class CaffeineCache : SimpleCache {
    private val cache = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(10.minutes.toJavaDuration())
        .build<String, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String): T? = cache.getIfPresent(key) as T?

    override fun <T> set(key: String, value: T?) {
        // null 值不会缓存，防止产生不必要的问题
        if (value == null) return
        cache.put(key, value)
    }

    override fun delete(key: String) = cache.invalidate(key)

    override fun clear() = cache.invalidateAll()
}
