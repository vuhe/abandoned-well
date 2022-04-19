package top.vuhe.admin.system.repository

import org.ktorm.dsl.*
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.BaseRepository
import top.vuhe.admin.spring.database.table.IdTable

@Repository
class LinkUserRole : BaseRepository() {

    override val cacheName: String? get() = UserRole.tableName

    private object UserRole : IdTable<Nothing>("sys_user_role") {
        override val id = varchar("id").primaryKey()
        val userId = varchar("user_id")
        val roleId = varchar("role_id")
    }

    /**
     * 通过 userId 查询 roleId 列表
     */
    fun selectRoleIdByUserId(userId: String): Set<String> = cacheable(userId) {
        database.from(UserRole)
            .select(UserRole.roleId)
            .where { UserRole.userId eq userId }
            .mapNotNull { it[UserRole.roleId] }
            .toSet()
    }

    /**
     * 插入 user - role 关联
     */
    fun insert(userId: String, roleIds: Collection<String>): Int {
        cache.delete(userId)
        database.delete(UserRole) { UserRole.userId eq userId }
        // 新建映射
        val result = database.batchInsert(UserRole) {
            roleIds.forEach { roleId ->
                item {
                    set(it.id, defaultId())
                    set(UserRole.userId, userId)
                    set(UserRole.roleId, roleId)
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

        cache.clear()
        return database.delete(UserRole) { UserRole.userId inList ids }
    }

    /**
     * 通过 roleId 删除关联
     */
    fun deleteByRole(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cache.clear()
        return database.delete(UserRole) { UserRole.roleId inList ids }
    }
}
