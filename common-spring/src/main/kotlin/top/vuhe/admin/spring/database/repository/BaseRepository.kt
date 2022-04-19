package top.vuhe.admin.spring.database.repository

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import top.vuhe.admin.api.cache.ProjectCache
import top.vuhe.admin.api.extra.nanoId

/**
 * ## 基本数据库访问层
 *
 * 此类会在事务中执行，spring 会创建 cglib 代理的子类，
 * 由于使用 Objenesis 而非构造方法创建，类中的字段全为空，
 * 为保证使用正确，本类及子类不能含必要实体字段！
 */
abstract class BaseRepository {
    protected abstract val cacheName: String?
    protected val cache get() = ProjectCache(cacheName)

    @Autowired
    protected open lateinit var database: Database

    protected fun defaultId(): String = nanoId(15)

    protected inline fun <E> cacheable(key: String, block: () -> E): E {
        cache.get<E>(key)?.let { return it }
        return block().also { cache[key] = it }
    }
}
