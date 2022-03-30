package top.vuhe.admin.api.cache.scope

import top.vuhe.admin.api.cache.map.MemoryCacheMap
import top.vuhe.admin.api.cache.map.TimeoutCacheMap
import kotlin.time.Duration

/**
 * 内存缓存管理器
 *
 * @author vuhe
 */
internal object MemoryCacheScope : TimeoutCacheScope() {
    override fun <V : Any> createCache(name: String, timeout: Duration): TimeoutCacheMap<V> {
        return MemoryCacheMap(name, timeout)
    }
}
