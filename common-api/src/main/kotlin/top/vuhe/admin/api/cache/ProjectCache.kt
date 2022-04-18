package top.vuhe.admin.api.cache

/**
 * ## 系统数据缓存
 */
object ProjectCache {
    /**
     * 工厂方法，生成缓存对象
     */
    operator fun invoke(cacheable: Boolean): SimpleCache {
        return if (cacheable) CaffeineCache()
        else EmptyCache
    }
}
