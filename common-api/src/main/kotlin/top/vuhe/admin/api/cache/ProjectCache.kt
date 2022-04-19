package top.vuhe.admin.api.cache

import java.util.concurrent.ConcurrentHashMap

/**
 * ## 系统数据缓存
 */
object ProjectCache {
    private val map = ConcurrentHashMap<String, SimpleCache>()

    /**
     * 工厂方法，获取缓存对象
     */
    operator fun invoke(cacheName: String?): SimpleCache {
        if (cacheName == null) return EmptyCache
        map[cacheName]?.let { return it }
        return CaffeineCache().also { map[cacheName] = it }
    }
}
