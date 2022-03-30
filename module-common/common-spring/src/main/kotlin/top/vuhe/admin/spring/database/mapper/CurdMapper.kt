package top.vuhe.admin.spring.database.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.Column
import top.vuhe.admin.api.text.UUIDGenerator
import top.vuhe.admin.spring.database.entity.BaseEntity
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import java.time.LocalDateTime

/**
 * 数据库增删改查方法
 *
 * @author vuhe
 */
abstract class CurdMapper<E : BaseEntity>(tableName: String) : BaseMapper<E>(tableName) {
    protected abstract val id: Column<String>

    /**
     * 默认 id 生成方法
     */
    protected fun defaultId(): String = UUIDGenerator.create(15)

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
    open fun selectList(): List<E> {
        return database.from(this).select().map { createEntity(it) }
    }

    /**
     * 查询实体列表
     */
    open fun selectList(param: E): List<E> {
        return database.from(this).select().listFilter(param).map { createEntity(it) }
    }

    /**
     * 查询实体列表时的 where 条件
     */
    protected open fun Query.listFilter(param: E) = apply {
        // default is no where filter
    }

    /**
     * 数据库插入，
     * 如果有 create 字段会赋值插入
     *
     * @param entity 实体
     */
    open fun insert(entity: E): Int {
        entity.createTime = LocalDateTime.now()
        entity.createBy = currUserId
        return database.insert(this) {
            insertSetting(entity)
            set(id, defaultId())
        }
    }

    /**
     * 数据库批量插入，
     * 如果有 create 字段会赋值插入
     *
     * @param entities 实体集合
     */
    open fun batchInsert(entities: Collection<E>): Int {
        return database.batchInsert(this) {
            entities.forEach { e ->
                e.createTime = LocalDateTime.now()
                e.createBy = currUserId
                item {
                    insertSetting(e)
                    set(id, defaultId())
                }
            }
        }.reduce { a, b -> a + b }
    }

    /**
     * 数据库修改，
     * 如果有 update 字段会赋值插入
     *
     * @param entity 实体
     */
    open fun update(entity: E): Int {
        entity.updateTime = LocalDateTime.now()
        entity.updateBy = currUserId
        return database.update(this) {
            updateSetting(entity)
            where { id eq entity.id }
        }
    }

    /**
     * 数据库批量修改，
     * 如果有 update 字段会赋值插入
     *
     * @param entities 实体集合
     */
    open fun batchUpdate(entities: Collection<E>): Int {
        return database.batchUpdate(this) {
            entities.forEach { e ->
                e.updateTime = LocalDateTime.now()
                e.updateBy = currUserId
                item {
                    updateSetting(e)
                    where { id eq e.id }
                }
            }
        }.reduce { a, b -> a + b }
    }

    /**
     * 数据库删除
     *
     * @param id 实体 id
     */
    open fun delete(id: String): Int = batchDelete(listOf(id))

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
