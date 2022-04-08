package top.vuhe.admin.api.cache

interface SimpleCache {
    operator fun <T : Any> get(scope: String, key: String): T?
    operator fun <T : Any> set(scope: String, key: String, value: T)
    fun delete(scope: String, key: String)
    fun clear(scope: String)
}
