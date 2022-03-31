package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.mapper.SysRoleMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import top.vuhe.admin.system.service.ISysUserService
import java.time.LocalDateTime

/**
 * 用户服务实现类
 *
 * @author vuhe
 */
@Service
class SysUserServiceImpl(
    private val sysUserMapper: SysUserMapper,
    private val sysRoleMapper: SysRoleMapper
) : CurdService<SysUser>(sysUserMapper), ISysUserService {

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: SysUser): Boolean {
        // 禁止用户名重复
        if (sysUserMapper.selectByUsername(entity.username) != null) {
            return false
        }
        entity.createTime = LocalDateTime.now()
        // 插入用户
        return super.add(entity)
    }

    /**
     * 删除用户会清除全部缓存
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        return super.batchRemove(ids)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modifyPassword(userId: String, password: String): Boolean {
        return sysUserMapper.updatePassword(userId, password) > 0
    }

    /**
     * 更改用户角色映射会删除缓存
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun saveUserRole(userId: String, roleIds: List<String>): Boolean {
        return sysUserMapper.batchInsertUserRole(userId, roleIds) > 0
    }

    override fun getUserRole(userId: String): List<SysRole> {
        // 查询全部角色
        val allRole = sysRoleMapper.selectList()
        // 查此用户的角色表
        val selectRole = sysUserMapper.selectRoleIdByUserId(userId)
        // 设置角色选中
        allRole.forEach { sysRole: SysRole ->
            if (sysRole.roleId in selectRole) {
                sysRole.checked = true
            }
        }
        return allRole
    }
}
