package top.vuhe.admin.api.cache.group

import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

/**
 * redis 实现有过期时间内存
 *
 * @author vuhe
 */
class RedisCache(
    private val managerName: String,
    name: String,
    allowNullValues: Boolean,
    timeout: Duration,
    private val redisTemplate: RedisTemplate<String, Any>
) : TimeoutCache(name, allowNullValues, timeout) {

    override fun iterator(): Iterator<Pair<Any, Any?>> {
        val redisKey = "$managerName:$name*"
        return object : Iterator<Pair<Any, Any?>> {
            private val it = redisTemplate.keys(redisKey).iterator()
            override fun hasNext(): Boolean = it.hasNext()
            override fun next(): Pair<Any, Any?> = it.next().let { i ->
                i to redisTemplate.opsForValue().get(i)
            }
        }
    }

    override fun getValue(key: Any): Any? {
        val redisKey = "$managerName:$name:$key"
        // 数据延期
        return redisTemplate.opsForValue().getAndExpire(redisKey, timeout)
    }

    override fun putValue(key: Any, value: Any?) {
        val redisKey = "$managerName:$name:$key"
        if (value != null) {
            redisTemplate.opsForValue().set(redisKey, value, timeout)
        }
    }

    override fun removeIfPresent(key: Any): Boolean {
        val redisKey = "$managerName:$name:$key"
        val value = redisTemplate.opsForValue().getAndDelete(redisKey)
        return value != null
    }

    override fun clearIfNotEmpty(): Boolean {
        val redisKey = "$managerName:$name*"
        val keySet = redisTemplate.keys(redisKey)
        val result = redisTemplate.delete(keySet) ?: 0
        return result > 0
    }
}
