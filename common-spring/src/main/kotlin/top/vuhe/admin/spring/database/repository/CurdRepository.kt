package top.vuhe.admin.spring.database.repository

import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.request.PageParam

abstract class CurdRepository<T, E>(cacheable: Boolean = false) :
    BaseRepository(cacheable) where E : Entity<E>, T : IdTable<E> {

    protected abstract val table: T

    protected abstract var E.entityId: String

    protected val entities get() = database.sequenceOf(table)

    protected fun EntitySequence<E, *>.toPage(params: PageParam): TablePage<E> {
        val count = cacheable("count") { entities.count() }
        val list = drop(params.offset).take(params.limit).toList()
        return TablePage(count, list)
    }

    /*---------------------------------------- CURD api ----------------------------------------*/

    fun selectById(queryId: String): E? = cacheable(queryId) {
        entities.find { it.id eq queryId }
    }

    fun selectList(): List<E> = cacheable("all") {
        entities.toList()
    }

    fun selectList(params: PageParam): List<E> {
        val declaring = params.query()

        val filtered = if (declaring == null) entities
        else entities.filter { declaring }

        return filtered.toList()
    }

    fun selectPage(params: PageParam): TablePage<E> {
        val declaring = params.query()

        val filtered = if (declaring == null) entities
        else entities.filter { declaring }

        return filtered.toPage(params)
    }

    fun insert(entity: E): Int {
        cache.delete("count")
        cache.delete("all")

        if (entity.entityId.isBlank()) {
            entity.entityId = defaultId()
        }

        return entities.add(entity)
    }

    fun update(entity: E): Int {
        cache.delete(entity.entityId)
        cache.delete("all")
        return entities.update(entity)
    }

    fun delete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        ids.forEach { cache.delete(it) }
        cache.delete("all")
        return entities.removeIf { it.id inList ids }
    }

}
