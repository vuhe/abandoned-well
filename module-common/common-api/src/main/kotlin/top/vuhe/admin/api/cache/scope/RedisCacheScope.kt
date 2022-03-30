package top.vuhe.admin.api.cache.scope

import org.springframework.data.redis.core.RedisTemplate
import top.vuhe.admin.api.cache.map.RedisCacheMap
import top.vuhe.admin.api.cache.map.TimeoutCacheMap
import kotlin.time.Duration

/**
 * redis 缓存管理器
 *
 * @author vuhe
 */
internal object RedisCacheScope : TimeoutCacheScope() {
    // need init this template!
    internal lateinit var redisTemplate: RedisTemplate<String, Any>

    override fun <V : Any> createCache(name: String, timeout: Duration): TimeoutCacheMap<V> {
        return RedisCacheMap(name, timeout, redisTemplate)
    }
}
