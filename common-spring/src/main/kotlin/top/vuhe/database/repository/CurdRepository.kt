package top.vuhe.database.repository

import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import top.vuhe.database.table.IdTable
import top.vuhe.database.table.TablePage
import top.vuhe.web.request.PageParam

/**
 * ## 增删改查访问层
 *
 * 本层的主要用途是提供对于实体数据的增删改查，
 * 大部分方法均可以在此提供，如果有特殊查询，请子类自行添加；
 * 此外，本类不能添加必要实体字段，详情见 [BaseRepository]
 */
abstract class CurdRepository<T, E> : BaseRepository()
        where E : Entity<E>, T : IdTable<E> {

    override val cacheName: String? get() = null

    protected abstract val table: T

    protected abstract var E.entityId: String

    protected val entities get() = database.sequenceOf(table)

    protected fun EntitySequence<E, *>.toPage(params: PageParam): TablePage<E> {
        val count = count()
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
