package top.vuhe.admin.api.cache

import top.vuhe.admin.api.cache.scope.MemoryCacheScope
import top.vuhe.admin.api.cache.scope.TimeoutCacheScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private val cacheScope: TimeoutCacheScope = MemoryCacheScope

/**
 * 缓存获取，如果存在直接获取，不存在调用缓存函数获取
 */
inline fun <T : Any> cacheable(
    scope: String, key: String, timeout: Duration = 10.minutes, block: () -> T
): T {
    val v = cacheGet<T>(scope, key)
    if (v != null) {
        return v
    }

    return cachePut(scope, key, block(), timeout)
}

/**
 * 缓存数据，放入缓存
 */
fun <T : Any> cachePut(
    scope: String, key: String, value: T, timeout: Duration = 10.minutes
): T {
    val cache = cacheScope.get<T>(scope)
    cache[key, timeout] = value
    return value
}

/**
 * 缓存获取，不存在为 null
 */
fun <T : Any> cacheGet(scope: String, key: String): T? {
    return cacheScope.get<T>(scope)[key]
}

/**
 * 删除缓存
 */
fun cacheDelete(scope: String, key: String) {
    val cache = cacheScope.get<Any>(scope)
    cache delete key
}

/**
 * 清除缓存
 */
fun cacheClear(scope: String) {
    cacheScope.get<Any>(scope).clear()
}
