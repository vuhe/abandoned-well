package top.vuhe.admin.api.cache.manager

import top.vuhe.admin.api.cache.TimeoutCacheProperties
import top.vuhe.admin.api.cache.group.MemoryCache
import top.vuhe.admin.api.cache.group.TimeoutCache
import java.time.Duration

/**
 * 内存缓存管理器
 *
 * @author vuhe
 */
class MemoryCacheManager(
    properties: TimeoutCacheProperties
) : TimeoutCacheManager(properties.cacheNames, properties.timeout) {
    private val allowNullValues: Boolean = properties.isCacheNullValues

    override fun createCache(name: String, timeout: Duration): TimeoutCache {
        val cache = cacheNames[name]
        return if (cache == null) {
            val c = MemoryCache(name, allowNullValues, timeout)
            cacheNames[name] = c
            c
        } else cache
    }

}
