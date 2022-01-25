package top.vuhe.admin.api.cache.group

import cn.hutool.cache.CacheUtil
import java.time.Duration

/**
 * 内存实现有过期时间内存
 *
 * @author vuhe
 */
class MemoryCache(
    name: String, allowNullValues: Boolean, timeout: Duration
) : TimeoutCache(name, allowNullValues, timeout) {
    private val timedCache = CacheUtil.newTimedCache<Any, Any>(timeout.toMillis())

    override fun iterator(): Iterator<Pair<Any, Any>> {
        return object : Iterator<Pair<Any, Any>> {
            private val it = timedCache.cacheObjIterator()
            override fun hasNext(): Boolean = it.hasNext()
            override fun next(): Pair<Any, Any> = it.next().let { i -> i.key to i.value }
        }
    }

    override fun getValue(key: Any): Any? {
        // 数据延期 (isUpdateLastAccess = true)
        return timedCache.get(key, true)
    }

    override fun putValue(key: Any, value: Any?) {
        timedCache.put(key, value)
    }

    override fun removeIfPresent(key: Any): Boolean {
        val isPresent = timedCache.containsKey(key)
        timedCache.remove(key)
        return isPresent
    }

    override fun clearIfNotEmpty(): Boolean {
        val notEmpty = !timedCache.isEmpty
        timedCache.clear()
        return notEmpty
    }

}
