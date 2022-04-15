package top.vuhe.admin.system.service.impl

import org.ktorm.entity.Entity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.exception.businessRequire
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.repository.LinkUserRole
import top.vuhe.admin.system.repository.SysRoleRepository
import top.vuhe.admin.system.repository.SysUserRepository
import top.vuhe.admin.system.service.ISysUserService
import java.time.LocalDateTime

/**
 * 用户服务实现类
 *
 * @author vuhe
 */
@Service
class SysUserServiceImpl(
    private val sysUserRepository: SysUserRepository,
    private val linkUserRole: LinkUserRole,
    private val sysRoleRepository: SysRoleRepository
) : CurdService<SysUser>(sysUserRepository), ISysUserService {
    private val encoder = BCryptPasswordEncoder()

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: SysUser): Boolean {
        businessRequire(
            sysUserRepository.selectByUsername(entity.username) == null
        ) { "用户名重复，请更改用户名" }

        entity.password = encoder.encode(entity.password)
        entity.createTime = LocalDateTime.now()
        // 插入用户
        return super.add(entity)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 删除关联表信息
        linkUserRole.deleteByUser(ids)
        // 删除用户信息
        return super.batchRemove(ids)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modifyPassword(userId: String, password: String): Boolean {
        val update = Entity.create<SysUser>().apply {
            this.userId = userId
            this.password = encoder.encode(password)
        }
        return sysUserRepository.update(update) > 0
    }

    /**
     * 更改用户角色映射会删除缓存
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun saveUserRole(userId: String, roleIds: List<String>): Boolean {
        return linkUserRole.insert(userId, roleIds) > 0
    }

    override fun getUserRole(userId: String): List<SysRole> {
        // 查询全部角色
        val allRole = sysRoleRepository.selectList()
        // 查此用户的角色表
        val selectRole = linkUserRole.selectRoleIdByUserId(userId)
        // 设置角色选中
        allRole.forEach { sysRole: SysRole ->
            sysRole["checked"] = sysRole.roleId in selectRole
        }
        return allRole
    }
}
