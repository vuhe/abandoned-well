package top.vuhe.admin.api.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

object CaffeineCache {
    private val caches = HashMap<String, Cache<String, Any>>()

    operator fun <T : Any> get(scope: String, key: String): T? {
        @Suppress("UNCHECKED_CAST")
        return getCache(scope).getIfPresent(key) as? T
    }

    operator fun <T : Any> set(scope: String, key: String, value: T) {
        getCache(scope).put(key, value)
    }

    fun delete(scope: String, key: String) {
        getCache(scope).invalidate(key)
    }

    fun clear(scope: String) {
        getCache(scope).invalidateAll()
    }

    private fun getCache(scope: String): Cache<String, Any> {
        synchronized(caches) {
            val c = caches[scope]
            if (c != null) return c
            val cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(10.minutes.toJavaDuration())
                .build<String, Any>()
            caches[scope] = cache
            return cache
        }
    }
}
