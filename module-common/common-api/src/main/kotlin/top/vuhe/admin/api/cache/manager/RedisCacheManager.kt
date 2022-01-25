package top.vuhe.admin.api.cache.manager

import org.springframework.data.redis.core.RedisTemplate
import top.vuhe.admin.api.cache.TimeoutCacheProperties
import top.vuhe.admin.api.cache.group.RedisCache
import top.vuhe.admin.api.cache.group.TimeoutCache
import java.time.Duration

/**
 * redis 缓存管理器
 *
 * @author vuhe
 */
class RedisCacheManager(
    properties: TimeoutCacheProperties,
    private val redisTemplate: RedisTemplate<String, Any>
) : TimeoutCacheManager(properties.cacheNames, properties.timeout) {
    private val managerName: String = if (properties.redis.isUseKeyPrefix) {
        properties.redis.keyPrefix
    } else ""
    private val allowNullValues: Boolean = properties.isCacheNullValues

    override fun createCache(name: String, timeout: Duration): TimeoutCache {
        val cache = cacheNames[name]
        return if (cache == null) {
            val c = RedisCache(managerName, name, allowNullValues, timeout, redisTemplate)
            cacheNames[name] = c
            c
        } else cache
    }

}
