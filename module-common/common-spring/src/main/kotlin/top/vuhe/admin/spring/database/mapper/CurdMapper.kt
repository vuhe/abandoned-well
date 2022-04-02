package top.vuhe.admin.spring.database.mapper

import org.ktorm.dsl.*
import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 数据库增删改查方法
 *
 * @author vuhe
 */
abstract class CurdMapper<E : BaseEntity>(tableName: String) : CacheableMapper<E>(tableName) {
    /**
     * 根据 Id 查询实体
     */
    fun selectById(queryId: String): E? {
        val cache = cacheGet<E>(queryId)
        if (cache != null) return cache

        val result = database.from(this).select()
            .where { id eq queryId }
            .map { createEntity(it) }
            .getOrNull(0)

        if (result != null) cachePut(queryId, result)
        return result
    }

    /**
     * 查询全部实体列表
     */
    fun selectList(): List<E> {
        val cache = cacheGet<List<E>>("all")
        if (cache != null) return cache

        val list = database.from(this).select().map { createEntity(it) }
        cachePut("all", list)
        return list
    }

    /**
     * 查询实体列表
     */
    fun selectList(param: E): List<E> {
        return database.from(this).select().whereWithConditions {
            param.properties.forEach { p ->
                if (p.isChanged) it.add(p.sqlOp(this[p.column]))
            }
        }.map { createEntity(it) }
    }

    /**
     * 数据库插入
     *
     * @param entity 实体
     */
    fun insert(entity: E): Int {
        val result = database.insert(this) {
            entity.properties.asSequence()
                .filter { !it.isPrimary }
                .forEach { set(col(it.column), it.insertValue) }
            set(id, generateId(entity))
        }

        cacheDelete("all")
        return result
    }

    /**
     * 数据库修改，
     * 如果有 update 字段会赋值插入
     *
     * @param entity 实体
     */
    fun update(entity: E): Int {
        val idValue = entity.properties.find { it.isPrimary }?.rawValue as String
        cacheDelete(idValue)
        cacheDelete("all")

        return database.update(this) {
            entity.properties.forEach {
                if (it.isChanged && !it.isPrimary) {
                    set(col(it.column), it.updateValue)
                }
            }
            where { id eq idValue }
        }
    }

    /**
     * 数据库删除
     *
     * @param id 实体 id
     */
    fun delete(id: String): Int {
        cacheDelete(id)
        cacheDelete("all")

        return database.delete(this) { it.id eq id }
    }

    /**
     * 数据库批量删除
     *
     * @param ids 实体 id 列表
     */
    fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) {
            return 0
        }

        // 删除缓存
        ids.forEach { cacheDelete(it) }
        cacheDelete("all")

        return database.delete(this) { it.id inList ids }
    }
}
