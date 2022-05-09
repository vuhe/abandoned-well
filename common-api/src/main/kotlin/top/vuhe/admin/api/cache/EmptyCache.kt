package top.vuhe.admin.api.cache

/**
 * ## 缓存的空实现
 *
 * 此缓存不会产生任何作用
 *
 * @author vuhe
 */
internal object EmptyCache : SimpleCache {
    override fun <T> get(key: String): T? = null
    override fun <T> set(key: String, value: T?) = Unit
    override fun delete(key: String) = Unit
    override fun clear() = Unit
}
