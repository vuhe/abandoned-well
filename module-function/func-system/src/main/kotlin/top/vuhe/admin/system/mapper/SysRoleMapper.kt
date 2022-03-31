package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import top.vuhe.admin.api.cache.cacheClear
import top.vuhe.admin.api.cache.cacheDelete
import top.vuhe.admin.api.cache.cacheable
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysRole

/**
 * 系统角色接口
 *
 * @author vuhe
 */
object SysRoleMapper : CurdMapper<SysRole>("sys_role") {

    // user-power 映射表
    object RolePowerTable : Table<Nothing>("sys_role_power") {
        val id = varchar("id").primaryKey()
        val roleId = varchar("role_id")
        val powerId = varchar("power_id")
    }

    /**
     * 批量删除角色（同时删除关联表）
     */
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        ids.forEach { cacheDelete("role-power", key = it) }
        cacheClear("authority")

        database.delete(RolePowerTable) { it.roleId inList ids }
        database.delete(SysUserMapper.UserRoleTable) { it.roleId inList ids }
        return super.batchDelete(ids)
    }

    /* --------------------------------------- 新增方法 ---------------------------------------- */

    /**
     * 通过 roleId 查询 powerId 列表
     */
    fun selectPowerIdByRoleId(roleId: String): Set<String> =
        cacheable("role-power", key = roleId) {
            database.from(RolePowerTable)
                .select(RolePowerTable.powerId)
                .where { RolePowerTable.roleId eq roleId }
                .mapNotNull { it[RolePowerTable.roleId] }
                .toSet()
        }

    /**
     * 批量更改 role - power 映射
     */
    fun batchInsertRolePower(roleId: String, powerIds: List<String>): Int {
        cacheDelete("role-power", key = roleId)
        cacheClear("authority")

        // 删除原先的角色映射
        database.delete(RolePowerTable) { it.roleId eq roleId }
        // 新建映射
        val result = database.batchInsert(RolePowerTable) {
            powerIds.forEach { powerId ->
                item {
                    set(it.id, defaultId())
                    set(it.roleId, roleId)
                    set(it.powerId, powerId)
                }
            }
        }
        return result.reduce { acc, i -> acc + i }
    }
}
