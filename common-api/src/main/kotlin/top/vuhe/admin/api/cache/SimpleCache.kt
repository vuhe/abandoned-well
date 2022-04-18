package top.vuhe.admin.api.cache

/**
 * ## 系统简单缓存接口
 */
interface SimpleCache {
    operator fun <T> get(key: String): T?
    operator fun <T> set(key: String, value: T?)
    infix fun delete(key: String)
    fun clear()
}
