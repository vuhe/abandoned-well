package top.vuhe.admin.api.cache.map

import org.springframework.data.redis.core.RedisTemplate
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * redis 实现有过期时间内存
 *
 * @author vuhe
 */
internal class RedisCacheMap<V : Any>(
    name: String, override val timeout: Duration,
    private val redisTemplate: RedisTemplate<String, Any>
) : TimeoutCacheMap<V>(name) {
    private val javaDuration = timeout.toJavaDuration()

    override fun get(key: String): V? {
        val redisKey = "$name:$key"
        // 数据延期
        @Suppress("UNCHECKED_CAST")
        return redisTemplate.opsForValue().getAndExpire(redisKey, javaDuration) as V?
    }

    override fun set(key: String, timeout: Duration, value: V) {
        val redisKey = "$name:$key"
        redisTemplate.opsForValue().set(redisKey, value, javaDuration)
    }

    override fun delete(keys: Array<String>) {
        val keySet = keys.toSet()
        redisTemplate.delete(keySet)
    }

    override fun clear() {
        val redisKey = "$name*"
        val keySet = redisTemplate.keys(redisKey)
        redisTemplate.delete(keySet)
    }
}
