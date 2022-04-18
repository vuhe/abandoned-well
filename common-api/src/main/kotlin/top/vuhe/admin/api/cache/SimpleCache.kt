package top.vuhe.admin.api.cache

/**
 * ## 系统简单缓存接口
 */
interface SimpleCache {
    operator fun <T : Any> get(scope: String, key: String): T?
    operator fun <T : Any> set(scope: String, key: String, value: T)
    fun delete(scope: String, key: String)
    fun clear(scope: String)
}
