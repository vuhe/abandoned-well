package top.vuhe.admin.spring.database.repository

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import top.vuhe.admin.api.cache.ProjectCache
import top.vuhe.admin.api.extra.nanoId

abstract class BaseRepository(cacheable: Boolean) {
    protected val cache = ProjectCache(cacheable)

    // this is must be open, because kotlin-spring will skip this
    // if this is not open, spring auto wired can't handle this
    @Autowired
    protected open lateinit var database: Database

    protected fun defaultId(): String = nanoId(15)

    protected inline fun <E> cacheable(key: String, block: () -> E): E {
        cache.get<E>(key)?.let { return it }
        return block().also { cache[key] = it }
    }
}
