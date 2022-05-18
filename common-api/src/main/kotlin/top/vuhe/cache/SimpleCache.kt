package top.vuhe.cache

/**
 * ## 系统简单缓存接口
 *
 * @author vuhe
 */
interface SimpleCache {
    operator fun <T> get(key: String): T?
    operator fun <T> set(key: String, value: T?)
    infix fun delete(key: String)
    fun clear()
}
