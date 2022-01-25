package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.domain.SysRole

/**
 * 角色服务接口类
 *
 * @author vuhe
 */
interface ISysRoleService : ICurdService<SysRole> {

    /**
     * 查询角色数据
     */
    fun list(): List<SysRole> = list(SysRole())

    /**
     * 获取角色权限
     */
    fun getRolePower(roleId: String): List<SysPower>

    /**
     * 保存角色权限
     */
    fun saveRolePower(roleId: String, powerIds: List<String>): Boolean
}
