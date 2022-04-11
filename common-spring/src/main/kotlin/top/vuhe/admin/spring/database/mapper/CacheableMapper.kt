package top.vuhe.admin.spring.database.mapper

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.expression.OrderByExpression
import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.admin.api.cache.ProjectCache
import top.vuhe.admin.api.text.UUIDGenerator
import top.vuhe.admin.spring.config.SystemConfiguration
import top.vuhe.admin.spring.database.entity.BaseEntity
import kotlin.reflect.full.createInstance

/**
 * 数据库表查询拓展
 *
 * @author vuhe
 */
abstract class CacheableMapper<E : BaseEntity>(tableName: String) : BaseTable<E>(tableName) {
    protected val database: Database get() = SystemConfiguration.database
    private var cacheEnable: Boolean = false

    init {
        entityClass?.apply {
            createInstance().properties().forEach {
                val column = registerColumn(it.column, it.sqlType)
                if (it.isPrimary) column.primaryKey()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected val id: Column<String> = primaryKeys[0] as Column<String>

    /*------------------------------------- select -------------------------------------------*/

    protected inline fun selectByConditions(
        vararg orders: OrderByExpression = emptyArray(),
        block: (MutableList<ColumnDeclaring<Boolean>>) -> Unit
    ): List<E> {
        val query = database.from(this).select().whereWithConditions(block)
        if (orders.isNotEmpty()) query.orderBy(*orders)
        return query.map { createEntity(it) }
    }

    /**
     * 获取 column 便于生成 dsl
     */
    @Suppress("UNCHECKED_CAST")
    protected fun col(name: String): Column<Any> {
        return get(name) as Column<Any>
    }

    /*------------------------------------- insert -------------------------------------------*/

    /**
     * id 生成方法
     */
    protected open fun generateId(entity: E): String = defaultId()

    /**
     * 默认 id 生成方法
     */
    protected fun defaultId(): String = UUIDGenerator.create(15)

    /*------------------------------------- cache -------------------------------------------*/

    protected fun enableCache() {
        cacheEnable = true
    }

    protected fun <T : Any> cachePut(key: String, value: T) {
        if (!cacheEnable) return
        ProjectCache[tableName, key] = value
    }

    protected fun <T : Any> cacheGet(key: String): T? {
        if (!cacheEnable) return null
        return ProjectCache[tableName, key]
    }

    protected fun cacheDelete(key: String) {
        if (!cacheEnable) return
        ProjectCache.delete(tableName, key)
    }

    protected fun cacheClear() {
        if (!cacheEnable) return
        ProjectCache.clear(tableName)
    }

    /*------------------------------------- impl -------------------------------------------*/

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): E {
        val entity = entityClass?.createInstance() ?: error("No entity class configured for table: '$this'")
        entity.properties().forEach { it.rawValue = row[this[it.column]] }
        return entity
    }
}
