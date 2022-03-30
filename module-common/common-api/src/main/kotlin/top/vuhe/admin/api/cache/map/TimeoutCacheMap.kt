package top.vuhe.admin.api.cache.map

import kotlin.time.Duration

/**
 * 有过期时间的缓存
 *
 * @author vuhe
 */
abstract class TimeoutCacheMap<V : Any>(val name: String) {
    protected abstract val timeout: Duration
    abstract operator fun get(key: String): V?
    operator fun set(key: String, value: V) = set(key, timeout, value)
    abstract operator fun set(key: String, timeout: Duration, value: V)
    infix fun delete(key: String) = delete(arrayOf(key))
    abstract infix fun delete(keys: Array<String>)
    abstract fun clear()
}
