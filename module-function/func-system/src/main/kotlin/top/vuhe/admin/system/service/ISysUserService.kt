package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.domain.SysRole

/**
 * 用户服务接口类
 *
 * @author vuhe
 */
interface ISysUserService : ICurdService<SysUser> {

    /**
     * 查询用户列表数据
     */
    fun list(): List<SysUser> = list(SysUser())

    /**
     * 修改用户密码
     */
    fun modifyPassword(userId: String, password: String): Boolean

    /**
     * 保存用户角色数据
     */
    fun saveUserRole(userId: String, roleIds: List<String>): Boolean

    /**
     * 获取用户角色数据
     */
    fun getUserRole(userId: String): List<SysRole>
}
