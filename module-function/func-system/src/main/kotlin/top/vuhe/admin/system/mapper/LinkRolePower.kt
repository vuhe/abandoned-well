package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import top.vuhe.admin.spring.database.entity.BaseEntity
import top.vuhe.admin.spring.database.mapper.CacheableMapper

object LinkRolePower : CacheableMapper<LinkRolePower.RolePower>("sys_role_power") {
    /**
     * 通过 roleId 查询 powerId 列表
     */
    fun selectPowerIdByRoleId(roleId: String): Set<String> {
        val cache = cacheGet<Set<String>>(roleId)
        if (cache != null) return cache

        val set = database.from(this)
            .select(col("power_id"))
            .where { col("role_id") eq roleId }
            .mapNotNull { it[col("role_id")] as? String }
            .toSet()

        cachePut(roleId, set)
        return set
    }

    /**
     * 插入 role - power 关联
     */
    fun insert(roleId: String, powerIds: Collection<String>): Int {
        cacheDelete(roleId)
        database.delete(this) { col("role_id") eq roleId }
        // 新建映射
        val result = database.batchInsert(this) {
            powerIds.forEach { powerId ->
                item {
                    set(it.id, defaultId())
                    set(col("role_id"), roleId)
                    set(col("power_id"), powerId)
                }
            }
        }
        return result.reduce { acc, i -> acc + i }
    }

    /**
     * 通过 roleId 删除关联
     */
    fun deleteByRole(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cacheClear()
        return database.delete(this) { col("role_id") inList ids }
    }

    /**
     * 通过 powerId 删除关联
     */
    fun deleteByPower(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cacheClear()
        return database.delete(this) { col("power_id") inList ids }
    }

    class RolePower : BaseEntity() {
        val id by varchar("id").primary()
        val roleId by varchar("role_id")
        val powerId by varchar("power_id")
    }
}
