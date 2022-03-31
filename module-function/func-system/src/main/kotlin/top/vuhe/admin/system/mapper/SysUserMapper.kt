package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import top.vuhe.admin.api.cache.cacheDelete
import top.vuhe.admin.api.cache.cacheGet
import top.vuhe.admin.api.cache.cachePut
import top.vuhe.admin.api.cache.cacheable
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysUser

/**
 * 用户接口层
 *
 * @author vuhe
 */
object SysUserMapper : CurdMapper<SysUser>("sys_user") {

    // user-role 映射表
    object UserRoleTable : Table<Nothing>("sys_user_role") {
        val id = varchar("id").primaryKey()
        val userId = varchar("user_id")
        val roleId = varchar("role_id")
    }

    override fun selectById(queryId: String): SysUser? {
        // 由于不存在空白串id，此处可提高调用速度
        // 系统中使用id检查比较多，高频调用
        if (queryId.isBlank()) return null
        val cache = cacheGet<SysUser>("user", key = queryId)
        if (cache != null) return cache
        val value = super.selectById(queryId)
        if (value != null) cachePut("user", key = queryId, value)
        return value
    }

    /**
     * 更改
     */
    override fun update(entity: SysUser): Int {
        cacheDelete("user", key = entity.userId)

        return super.update(entity)
    }

    /**
     * 批量删除用户（同时删除关联表）
     */
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        ids.forEach {
            cacheDelete("user", key = it)
            cacheDelete("user-role", key = it)
            cacheDelete("authority", key = it)
        }

        // 在执行本表删除前，先删除 user - role 表数据
        database.delete(UserRoleTable) { it.userId inList ids }
        return super.batchDelete(ids)
    }

    /* --------------------------------------- 新增方法 ---------------------------------------- */

    /**
     * 通过 username 查询用户
     */
    fun selectByUsername(username: String): SysUser? {
        val list = database.from(this).select()
            .where { col("username") eq username }
            .map { createEntity(it) }
        return list.getOrNull(0)
    }

    /**
     * 通过 userId 查询 roleId 列表
     */
    fun selectRoleIdByUserId(userId: String): Set<String> =
        cacheable("user-role", key = userId) {
            database.from(UserRoleTable)
                .select(UserRoleTable.roleId)
                .where { UserRoleTable.userId eq userId }
                .mapNotNull { it[UserRoleTable.roleId] }
                .toSet()
        }

    /**
     * 更改密码
     */
    fun updatePassword(userId: String, password: String) = update(SysUser().apply {
        this.userId = userId
        this.password = password
    })

    /**
     * 批量更改 user - role 映射
     */
    fun batchInsertUserRole(userId: String, roleIds: List<String>): Int {
        cacheDelete("user-role", key = userId)
        cacheDelete("authority", key = userId)

        // 删除原先的角色映射
        database.delete(UserRoleTable) { it.userId eq userId }
        // 新建映射
        val result = database.batchInsert(UserRoleTable) {
            roleIds.forEach { roleId ->
                item {
                    set(it.id, defaultId())
                    set(it.userId, userId)
                    set(it.roleId, roleId)
                }
            }
        }
        return result.reduce { acc, i -> acc + i }
    }

    /**
     * 批量删除部门信息
     */
    fun batchDeleteDept(deptIds: Collection<String>): Int {
        return database.update(this) {
            set(col("dept_id"), "")
            where { col("dept_id") inList deptIds }
        }
    }
}
