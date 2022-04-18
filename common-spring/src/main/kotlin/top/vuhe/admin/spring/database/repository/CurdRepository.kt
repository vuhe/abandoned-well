package top.vuhe.admin.spring.database.repository

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.request.PageParam

abstract class CurdRepository<T, E>(cacheable: Boolean = false) :
    BaseRepository(cacheable) where E : Entity<E>, T : IdTable<E> {
    final override val cacheName: String get() = table.tableName

    protected abstract val table: T

    protected val entities get() = database.sequenceOf(table)

    protected abstract var E.entityId: String

    protected open fun find(params: PageParam): List<ColumnDeclaring<Boolean>> = emptyList()

    /*---------------------------------------- open api ----------------------------------------*/

    fun count(): Int = cacheable("count") {
        entities.count()
    }

    fun selectById(queryId: String): E? = cacheable(queryId) {
        entities.find { it.id eq queryId }
    }

    fun selectList(): List<E> = cacheable("all") {
        entities.toList()
    }

    fun selectList(params: PageParam): List<E> {
        val declaring = find(params)

        val filtered = if (declaring.isEmpty()) entities
        else entities.filter { declaring.reduce { a, b -> a and b } }

        return filtered.toList()
    }

    fun selectPage(params: PageParam): TablePage<E> {
        val declaring = find(params)

        val filtered = if (declaring.isEmpty()) entities
        else entities.filter { declaring.reduce { a, b -> a and b } }

        val list = filtered.drop(params.offset).take(params.limit).toList()

        return TablePage(count(), list)
    }

    fun insert(entity: E): Int {
        cacheDelete("count")
        cacheDelete("all")

        if (entity.entityId.isBlank()) {
            entity.entityId = defaultId()
        }

        return entities.add(entity)
    }

    fun update(entity: E): Int {
        cacheDelete(entity.entityId)
        cacheDelete("all")
        return entities.update(entity)
    }

    fun delete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        ids.forEach { cacheDelete(it) }
        cacheDelete("all")
        return entities.removeIf { it.id inList ids }
    }

}
