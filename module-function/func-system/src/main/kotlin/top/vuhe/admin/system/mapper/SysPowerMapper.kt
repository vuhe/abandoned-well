package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import top.vuhe.admin.api.cache.cacheClear
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysPower

/**
 * 系统权限接口
 *
 * @author vuhe
 */
object SysPowerMapper : CurdMapper<SysPower>("sys_power") {

    override fun selectList(param: SysPower): List<SysPower> {
        return super.selectList(param).sortedBy { it.sort }
    }

    override fun update(entity: SysPower): Int {
        cacheClear("authority")
        return super.update(entity)
    }

    /**
     * 批量删除角色（同时删除关联表）
     */
    override fun batchDelete(ids: Collection<String>): Int {
        if (ids.isEmpty()) return 0
        cacheClear("authority")

        database.delete(SysRoleMapper.RolePowerTable) { it.powerId inList ids }
        return super.batchDelete(ids)
    }

    /* --------------------------------------- 新增方法 ---------------------------------------- */

    /**
     * 通过 parentId 列表查询
     */
    fun selectByParentId(parentId: String): List<SysPower> {
        return selectByConditions {
            it.add(col("parent_id") eq parentId)
        }
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
                it.add(col("enable") eq true)
            }
            .map { createEntity(it) }
    }
}
