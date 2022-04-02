package top.vuhe.admin.system.mapper

import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.dsl.update
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysUser

/**
 * 用户接口层
 *
 * @author vuhe
 */
object SysUserMapper : CurdMapper<SysUser>("sys_user") {
    init {
        enableCache()
    }

    /**
     * 通过 username 查询用户
     */
    fun selectByUsername(username: String): SysUser? {
        val list = selectByConditions { it.add(col("username") eq username) }
        return list.getOrNull(0)
    }

    /**
     * 批量删除部门信息
     */
    fun batchDeleteDept(deptIds: Collection<String>): Int {
        cacheClear()
        return database.update(this) {
            set(col("dept_id"), "")
            where { col("dept_id") inList deptIds }
        }
    }
}
