package top.vuhe.admin.system.repository

import org.ktorm.dsl.*
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.BaseRepository
import top.vuhe.admin.spring.database.table.IdTable

@Repository
class LinkRolePower : BaseRepository(cacheable = true) {

    private object RolePower : IdTable<Nothing>("sys_role_power") {
        override val id = varchar("id").primaryKey()
        val roleId = varchar("role_id")
        val powerId = varchar("power_id")
    }

    /**
     * 通过 roleId 查询 powerId 列表
     */
    fun selectPowerIdByRoleId(roleId: String): Set<String> = cacheable(roleId) {
        database.from(RolePower)
            .select(RolePower.powerId)
            .where { RolePower.roleId eq roleId }
            .mapNotNull { it[RolePower.powerId] }
            .toSet()
    }

    /**
     * 插入 role - power 关联
     */
    fun insert(roleId: String, powerIds: Collection<String>): Int {
        cache.delete(roleId)
        database.delete(RolePower) { RolePower.roleId eq roleId }
        // 新建映射
        val result = database.batchInsert(RolePower) {
            powerIds.forEach { powerId ->
                item {
                    set(it.id, defaultId())
                    set(RolePower.roleId, roleId)
                    set(RolePower.powerId, powerId)
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

        cache.clear()
        return database.delete(RolePower) { RolePower.roleId inList ids }
    }

    /**
     * 通过 powerId 删除关联
     */
    fun deleteByPower(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0

        cache.clear()
        return database.delete(RolePower) { RolePower.powerId inList ids }
    }
}
