package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysPower

/**
 * 系统权限接口
 *
 * @author vuhe
 */
@Repository
class SysPowerMapper : CurdMapper<SysPower>("sys_power") {
    override val id = varchar("power_id").primaryKey().bind(SysPower::powerId)
    private val powerName = varchar("power_name").bind(SysPower::powerName)
    private val powerType = varchar("power_type").bind(SysPower::powerType)
    private val powerCode = varchar("power_code").bind(SysPower::powerCode)
    private val powerUrl = varchar("power_url").bind(SysPower::powerUrl)
    private val openType = varchar("open_type").bind(SysPower::openType)
    private val parentId = varchar("parent_id").bind(SysPower::parentId)
    private val icon = varchar("icon").bind(SysPower::icon)
    private val sort = int("sort").bind(SysPower::sort, 0)
    private val enable = boolean("enable").bind(SysPower::enable, true)

    private val createTime = datetime("create_time").bind(SysPower::createTime)
    private val createBy = varchar("create_by").bind(SysPower::createBy)
    private val updateTime = datetime("update_time").bind(SysPower::updateTime)
    private val updateBy = varchar("update_by").bind(SysPower::updateBy)
    private val remark = varchar("remark").bind(SysPower::remark)

    override fun Query.listFilter(param: SysPower): Query {
        return whereWithConditions {
            if (param.powerId.isNotEmpty()) it.add(id eq param.powerId)
        }.orderBy(sort.asc())
    }

    @CacheEvict("authority", allEntries = true)
    override fun update(entity: SysPower): Int {
        return super.update(entity)
    }

    @CacheEvict("authority", allEntries = true)
    override fun batchUpdate(entities: Collection<SysPower>): Int {
        return super.batchUpdate(entities)
    }

    /**
     * 批量删除角色（同时删除关联表）
     */
    @CacheEvict("authority", allEntries = true)
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        database.delete(SysRoleMapper.RolePowerTable) { it.powerId inList ids }
        return super.batchDelete(ids)
    }

    /* --------------------------------------- 新增方法 ---------------------------------------- */

    /**
     * 通过 parentId 列表查询
     */
    fun selectByParentId(parentId: String): List<SysPower> {
        return database.from(this).select().where { this.parentId eq parentId }
            .map { createEntity(it) }
    }

    /**
     * 通过 id 列表查询
     */
    fun selectListByIds(ids: List<String>): List<SysPower> =
        selectListByIds(ids, false)

    /**
     * 通过 admin 查询列表
     */
    fun selectListByAdmin(): List<SysPower> =
        selectListByIds(emptyList(), true)

    /**
     * 通过 id 列表查询，默认不使用 admin 查询；
     *
     * admin 查询会忽略角色，直接查询全部
     */
    private fun selectListByIds(ids: List<String>, useAdmin: Boolean): List<SysPower> {
        if (!useAdmin && ids.isEmpty()) return emptyList()
        return database.from(this).selectDistinct(columns)
            .whereWithConditions {
                if (!useAdmin) {
                    it.add(id inList ids)
                }
                it.add(enable eq true)
            }
            .map { createEntity(it) }
    }
}
