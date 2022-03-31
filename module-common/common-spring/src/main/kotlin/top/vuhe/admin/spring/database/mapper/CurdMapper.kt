package top.vuhe.admin.spring.database.mapper

import org.ktorm.dsl.*
import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 数据库增删改查方法
 *
 * @author vuhe
 */
abstract class CurdMapper<E : BaseEntity>(tableName: String) : BaseMapper<E>(tableName) {
    /**
     * 根据 Id 查询实体
     */
    open fun selectById(queryId: String): E? {
        val list = database.from(this).select()
            .where { id eq queryId }
            .map { createEntity(it) }
        return list.getOrNull(0)
    }

    /**
     * 查询全部实体列表
     */
    fun selectList(): List<E> {
        return database.from(this).select().map { createEntity(it) }
    }

    /**
     * 查询实体列表
     */
    open fun selectList(param: E): List<E> {
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
    fun insert(entity: E): Int = database.insert(this) {
        entity.properties.asSequence()
            .filter { !it.isPrimary }
            .forEach { set(col(it.column), it.insertValue) }
        set(id, generateId(entity))
    }

    /**
     * 数据库修改，
     * 如果有 update 字段会赋值插入
     *
     * @param entity 实体
     */
    open fun update(entity: E): Int = database.update(this) {
        entity.properties.forEach {
            if (it.isChanged && !it.isPrimary) {
                set(col(it.column), it.updateValue)
            }
        }
        val idValue = entity.properties.find { it.isPrimary }?.rawValue as String
        where { id eq idValue }
    }

    /**
     * 数据库删除
     *
     * @param id 实体 id
     */
    fun delete(id: String): Int = batchDelete(listOf(id))

    /**
     * 数据库批量删除
     *
     * @param ids 实体 id 列表
     */
    open fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        return database.delete(this) { it.id inList ids }
    }
}
