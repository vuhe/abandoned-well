package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.entity.column.IdMaker
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysUser

/**
 * 用户接口层
 *
 * @author vuhe
 */
@Repository
class SysUserMapper : CurdMapper<SysUser>("sys_user") {
    override val id = varchar("user_id").primaryKey().bind(SysUser::userId)
    private val name = varchar("username").bind(SysUser::username)
    private val pwd = varchar("password").bind(SysUser::password)
    private val salt = varchar("salt").bind(SysUser::salt)
    private val realName = varchar("real_name").bind(SysUser::realName)
    private val email = varchar("email").bind(SysUser::email)
    private val avatar = varchar("avatar").bind(SysUser::avatar)
    private val sex = varchar("sex").bind(SysUser::sex)
    private val phone = varchar("phone").bind(SysUser::phone)
    private val deptId = varchar("dept_id").bind(SysUser::deptId)
    private val unlocked = boolean("status").bind(SysUser::unlocked, true)
    private val enable = boolean("enable").bind(SysUser::enable, true)
    private val admin = boolean("admin").bind(SysUser::admin, false)
    private val login = boolean("login").bind(SysUser::login, false)
    private val lastTime = datetime("last_time").bind(SysUser::lastTime)

    private val createTime = datetime("create_time").bind(SysUser::createTime)
    private val createBy = varchar("create_by").bind(SysUser::createBy)
    private val updateTime = datetime("update_time").bind(SysUser::updateTime)
    private val updateBy = varchar("update_by").bind(SysUser::updateBy)
    private val remark = varchar("remark").bind(SysUser::remark)

    // user-role 映射表
    object UserRoleTable : Table<Nothing>("sys_user_role") {
        val id = varchar("id").primaryKey()
        val userId = varchar("user_id")
        val roleId = varchar("role_id")
    }

    @Cacheable("user", key = "#queryId")
    override fun selectById(queryId: String): SysUser? {
        return super.selectById(queryId)
    }

    override fun Query.listFilter(param: SysUser): Query {
        return whereWithConditions {
            if (param.username.isNotEmpty()) it.add(name like "%${param.username}%")
            if (param.realName.isNotEmpty()) it.add(realName like "%${param.realName}%")
            if (param.deptId.isNotEmpty()) it.add(deptId like "%${param.deptId}%")
        }
    }

    /**
     * 加密密码后插入
     */
    override fun insert(entity: SysUser): Int {
        entity.password = BCryptPasswordEncoder().encode(entity.password)
        return super.insert(entity)
    }

    /**
     * 加密密码后，批量插入
     */
    override fun batchInsert(entities: Collection<SysUser>): Int {
        entities.forEach { it.password = BCryptPasswordEncoder().encode(it.password) }
        return super.batchInsert(entities)
    }

    /**
     * 加密密码后，更改
     */
    @CacheEvict("user", key = "#entity.userId")
    override fun update(entity: SysUser): Int {
        entity.password = ""
        return super.update(entity)
    }

    /**
     * 加密密码后，批量更改
     */
    @CacheEvict("user", allEntries = true)
    override fun batchUpdate(entities: Collection<SysUser>): Int {
        entities.forEach { it.password = "" }
        return super.batchUpdate(entities)
    }

    /**
     * 删除用户（同时删除关联表）
     */
    @Caching(
        evict = [
            CacheEvict("user", key = "#id"),
            CacheEvict("user-role", key = "#id"),
            CacheEvict("authority", key = "#id")
        ]
    )
    override fun delete(id: String): Int {
        database.delete(UserRoleTable) { it.userId eq id }
        return database.delete(this) { this.id eq id }
    }

    /**
     * 批量删除用户（同时删除关联表）
     */
    @Caching(
        evict = [
            CacheEvict("user", allEntries = true),
            CacheEvict("user-role", allEntries = true),
            CacheEvict("authority", allEntries = true)
        ]
    )
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
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
            .where { name eq username }
            .map { createEntity(it) }
        return list.getOrNull(0)
    }

    /**
     * 通过 userId 查询 roleId 列表
     */
    @Cacheable("user-role", key = "#userId")
    fun selectRoleIdByUserId(userId: String): Set<String> {
        return database.from(UserRoleTable)
            .select(UserRoleTable.roleId)
            .where { UserRoleTable.userId eq userId }
            .mapNotNull { it[UserRoleTable.roleId] }
            .toSet()
    }

    /**
     * 更改密码
     */
    fun updatePassword(userId: String, password: String): Int {
        return database.update(this) {
            set(it.pwd, BCryptPasswordEncoder().encode(password))
            where { id eq userId }
        }
    }

    /**
     * 批量更改 user - role 映射
     */
    @Caching(
        evict = [
            CacheEvict("user-role", key = "#userId"),
            CacheEvict("authority", key = "#userId")
        ]
    )
    fun batchInsertUserRole(userId: String, roleIds: List<String>): Int {
        // 删除原先的角色映射
        database.delete(UserRoleTable) { it.userId eq userId }
        // 新建映射
        val result = database.batchInsert(UserRoleTable) {
            roleIds.forEach { roleId ->
                item {
                    set(it.id, IdMaker.next().toString())
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
            set(it.deptId, "")
            where { it.deptId inList deptIds }
        }
    }
}
