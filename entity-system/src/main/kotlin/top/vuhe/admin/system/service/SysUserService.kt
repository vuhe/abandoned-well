package top.vuhe.admin.system.service

import org.ktorm.entity.Entity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.exception.businessRequire
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.spring.security.securityContext
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.repository.LinkUserRole
import top.vuhe.admin.system.repository.SysRoleRepository
import top.vuhe.admin.system.repository.SysUserRepository
import java.time.LocalDateTime

/**
 * 用户服务实现类
 *
 * @author vuhe
 */
@Service
class SysUserService(
    override val repository: SysUserRepository,
    private val linkUserRole: LinkUserRole,
    private val sysRoleRepository: SysRoleRepository
) : CurdService<SysUser>() {
    private val encoder = BCryptPasswordEncoder()
    private val currUserId by securityContext()

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: SysUser): Boolean {
        businessRequire(
            repository.selectByUsername(entity.username) == null
        ) { "用户名重复，请更改用户名" }

        entity.password = encoder.encode(entity.password)
        entity.createTime = LocalDateTime.now()
        // 插入用户
        return super.add(entity)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(ids: List<String>): Boolean {
        businessRequire(
            ids.find { it == currUserId } == null
        ) { "不能删除自己" }
        linkUserRole.deleteByUser(ids)
        return super.remove(ids)
    }

    /** 修改用户密码 */
    @Transactional(rollbackFor = [Exception::class])
    fun modifyPassword(userId: String, password: String): Boolean {
        val update = Entity.create<SysUser>().apply {
            this.userId = userId
            this.password = encoder.encode(password)
        }
        return repository.update(update) > 0
    }

    /** 保存用户角色数据 */
    @Transactional(rollbackFor = [Exception::class])
    fun saveUserRole(userId: String, roleIds: List<String>): Boolean {
        return linkUserRole.insert(userId, roleIds) > 0
    }

    /** 获取用户角色数据 */
    fun getUserRole(userId: String): List<SysRole> {
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
