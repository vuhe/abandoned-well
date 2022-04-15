package top.vuhe.admin.system.service.impl

import org.ktorm.entity.Entity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.security.principal.LoginUser
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.repository.LinkRolePower
import top.vuhe.admin.system.repository.LinkUserRole
import top.vuhe.admin.system.repository.SysPowerRepository
import top.vuhe.admin.system.repository.SysUserRepository
import java.time.LocalDateTime

/**
 * 提供动态用户信息，防止数据不同步
 *
 * @author vuhe
 */
@Service
class LoginUserServiceImpl(
    private val sysUserRepository: SysUserRepository,
    private val linkUserRole: LinkUserRole,
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) : UserSecurityService {

    /**
     * 为减少数据库访问，此方法会缓存数据，在数据库发生数据更改时，
     * 会删除数据，此时会在此缓存
     */
    fun getUserById(id: String): SysUser? = sysUserRepository.selectById(id)

    /**
     * 此方法会缓存用户权限列表，出现权限变化时会清空缓存
     */
    fun getAuthorities(userId: String): List<String> {
        // 转换为 roleId
        val roleIds = linkUserRole.selectRoleIdByUserId(userId)

        // 转换为 powerId
        val powerIds = roleIds.map {
            linkRolePower.selectPowerIdByRoleId(it)
        }.flatten()

        // 查询 power 权限，加入列表
        return sysPowerRepository.selectListByIds(powerIds).map { it.powerCode }
    }

    override fun getLoginUserId(username: String): String? {
        return sysUserRepository.selectByUsername(username)?.userId
    }

    override fun getLoginUserById(userId: String): LoginUser {
        return SecurityUserProxy(userId, this)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateLoginTime(userId: String) {
        val user = Entity.create<SysUser>().apply {
            this.userId = userId
            lastTime = LocalDateTime.now()
        }
        sysUserRepository.update(user)
    }

    private class SecurityUserProxy(
        override val userId: String, private val service: LoginUserServiceImpl
    ) : LoginUser {
        private val user get() = service.getUserById(userId)
        override val password: String get() = user?.password ?: ""
        override val isAdmin: Boolean get() = user?.admin ?: false
        override val authorities: Collection<String> get() = service.getAuthorities(userId)
        override val isNonLocked: Boolean = user?.unlocked ?: false
        override val isEnable: Boolean get() = user?.enable ?: false
    }
}
