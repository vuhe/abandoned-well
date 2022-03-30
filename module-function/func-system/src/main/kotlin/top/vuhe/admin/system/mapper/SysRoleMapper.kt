package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysRole

/**
 * 系统角色接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class SysRoleMapper : CurdMapper<SysRole>("sys_role") {
    override val id = varchar("role_id").primaryKey().bind(SysRole::roleId)
    private val roleName = varchar("role_name").bind(SysRole::roleName)
    private val roleCode = varchar("role_code").bind(SysRole::roleCode)
    private val enable = boolean("enable").bind(SysRole::enable, true)
    private val details = varchar("details").bind(SysRole::details)
    private val sort = int("sort").bind(SysRole::sort, 0)

    private val createTime = datetime("create_time").bind(SysRole::createTime)
    private val createBy = varchar("create_by").bind(SysRole::createBy)
    private val updateTime = datetime("update_time").bind(SysRole::updateTime)
    private val updateBy = varchar("update_by").bind(SysRole::updateBy)
    private val remark = varchar("remark").bind(SysRole::remark)

    // user-power 映射表
    object RolePowerTable : Table<Nothing>("sys_role_power") {
        val id = varchar("id").primaryKey()
        val roleId = varchar("role_id")
        val powerId = varchar("power_id")
    }

    override fun Query.listFilter(param: SysRole): Query {
        return whereWithConditions {
            if (param.roleCode.isNotEmpty()) it.add(roleCode like "%${param.roleCode}%")
            if (param.roleName.isNotEmpty()) it.add(roleName like "%${param.roleName}%")
        }
    }

    /**
     * 删除角色（同时删除关联表）
     */
    @Caching(
        evict = [
            CacheEvict("role-power", key = "#id"),
            CacheEvict("authority", allEntries = true),
        ]
    )
    override fun delete(id: String): Int {
        database.delete(RolePowerTable) { it.roleId eq id }
        database.delete(SysUserMapper.UserRoleTable) { it.roleId eq id }
        return database.delete(this) { this.id eq id }
    }

    /**
     * 批量删除角色（同时删除关联表）
     */
    @Caching(
        evict = [
            CacheEvict("role-power", allEntries = true),
            CacheEvict("authority", allEntries = true),
        ]
    )
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        database.delete(RolePowerTable) { it.roleId inList ids }
        database.delete(SysUserMapper.UserRoleTable) { it.roleId inList ids }
        return super.batchDelete(ids)
    }

    /* --------------------------------------- 新增方法 ---------------------------------------- */

    /**
     * 通过 roleId 查询 powerId 列表
     */
    @Cacheable("role-power", key = "#roleId")
    fun selectPowerIdByRoleId(roleId: String): Set<String> {
        return database.from(RolePowerTable)
            .select(RolePowerTable.powerId)
            .where { RolePowerTable.roleId eq roleId }
            .mapNotNull { it[RolePowerTable.roleId] }
            .toSet()
    }

    /**
     * 批量更改 role - power 映射
     */
    @Caching(
        evict = [
            CacheEvict("role-power", key = "#roleId"),
            CacheEvict("authority", allEntries = true),
        ]
    )
    fun batchInsertRolePower(roleId: String, powerIds: List<String>): Int {
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
