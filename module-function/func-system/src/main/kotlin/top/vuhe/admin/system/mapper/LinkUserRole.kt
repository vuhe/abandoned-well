package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import top.vuhe.admin.spring.database.entity.BaseEntity
import top.vuhe.admin.spring.database.mapper.CacheableMapper

object LinkUserRole : CacheableMapper<LinkUserRole.UserRole>("sys_user_role") {
    init {
        enableCache()
    }

    /**
     * 通过 userId 查询 roleId 列表
     */
    fun selectRoleIdByUserId(userId: String): Set<String> {
        val cache = cacheGet<Set<String>>(userId)
        if (cache != null) return cache

        val set = database.from(this)
            .select(col("role_id"))
            .where { col("user_id") eq userId }
            .mapNotNull { it[col("role_id")] as? String }
            .toSet()

        cachePut(userId, set)
        return set
    }

    /**
     * 插入 user - role 关联
     */
    fun insert(userId: String, roleIds: Collection<String>): Int {
        cacheDelete(userId)
        database.delete(this) { col("user_id") eq userId }
        // 新建映射
        val result = database.batchInsert(this) {
            roleIds.forEach { roleId ->
                item {
                    set(it.id, defaultId())
                    set(col("user_id"), userId)
                    set(col("role_id"), roleId)
                }
            }
        }
        return result.reduce { acc, i -> acc + i }
    }

    /**
     * 通过 userId 删除关联
     */
    fun deleteByUser(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cacheClear()
        return database.delete(this) { col("user_id") inList ids }
    }

    /**
     * 通过 roleId 删除关联
     */
    fun deleteByRole(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cacheClear()
        return database.delete(this) { col("role_id") inList ids }
    }

    class UserRole : BaseEntity() {
        val id by varchar("id").primary()
        val userId by varchar("user_id")
        val roleId by varchar("role_id")
    }
}
