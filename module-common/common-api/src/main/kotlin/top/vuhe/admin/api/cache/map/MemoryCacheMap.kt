package top.vuhe.admin.api.cache.map

import cn.hutool.cache.CacheUtil
import kotlin.time.Duration

/**
 * 内存实现有过期时间内存
 *
 * @author vuhe
 */
internal class MemoryCacheMap<V : Any>(
    name: String, override val timeout: Duration
) : TimeoutCacheMap<V>(name) {
    private val timedCache = CacheUtil.newTimedCache<String, V>(timeout.inWholeMilliseconds)

    override fun get(key: String): V? =
        timedCache.get(key, true)

    override fun set(key: String, timeout: Duration, value: V) =
        timedCache.put(key, value, timeout.inWholeMilliseconds)

    override fun delete(keys: Array<String>) {
        keys.forEach { timedCache.remove(it) }
    }

    override fun clear() {
        timedCache.clear()
    }
}
