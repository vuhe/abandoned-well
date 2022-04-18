package top.vuhe.admin.spring.database.repository

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import top.vuhe.admin.api.cache.ProjectCache
import top.vuhe.admin.api.extra.nanoId

abstract class BaseRepository(private val cacheable: Boolean) {
    // this is must be open, because kotlin-spring will skip this
    // if this is not open, spring auto wired can't handle this
    @Autowired
    protected open lateinit var database: Database

    protected abstract val cacheName: String

    protected fun defaultId(): String = nanoId(15)

    protected inline fun <E> cacheable(key: String, block: () -> E): E {
        val cache = cacheGet<E>(key)
        if (cache != null) return cache

        val result = block()
        if (result != null) cachePut(key, result)
        return result
    }

    protected fun <T : Any> cachePut(key: String, value: T) {
        if (!cacheable) return
        ProjectCache[cacheName, key] = value
    }

    protected fun <T> cacheGet(key: String): T? {
        if (!cacheable) return null
        return ProjectCache[cacheName, key]
    }

    protected fun cacheDelete(key: String) {
        if (!cacheable) return
        ProjectCache.delete(cacheName, key)
    }

    protected fun cacheClear() {
        if (!cacheable) return
        ProjectCache.clear(cacheName)
    }
}
