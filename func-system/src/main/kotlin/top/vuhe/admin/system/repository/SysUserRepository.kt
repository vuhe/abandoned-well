package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.dsl.update
import org.ktorm.entity.find
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.table.SysUserTable

/**
 * 用户接口层
 *
 * @author vuhe
 */
@Repository
class SysUserRepository : CurdRepository<SysUserTable, SysUser>(cacheable = true) {

    override val table get() = SysUserTable

    override var SysUser.entityId: String by SysUser::userId

    /**
     * 通过 username 查询用户
     */
    fun selectByUsername(username: String): SysUser? {
        return entities.find { SysUserTable.username eq username }
    }

    /**
     * 批量删除部门信息
     */
    fun dropDept(deptIds: Collection<String>): Int {
        cache.clear()
        return database.update(SysUserTable) {
            set(it.deptId, "")
            where { it.deptId inList deptIds }
        }
    }
}
