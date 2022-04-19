package top.vuhe.admin.system.service

import org.ktorm.entity.Entity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
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
class ProjectSecurityService(
    private val logging: LoggingFactory,
    private val users: SysUserRepository,
    private val userRole: LinkUserRole,
    private val rolePower: LinkRolePower,
    private val powers: SysPowerRepository
) : UserSecurityService {

    fun getUserById(id: String): SysUser? = users.selectById(id)

    /**
     * 此方法会缓存用户权限列表，出现权限变化时会清空缓存
     */
    fun getAuthorities(userId: String): List<String> {
        // 转换为 roleId
        val roleIds = userRole.selectRoleIdByUserId(userId).asSequence()

        // 转换为 powerId
        val powerIds = roleIds.map {
            rolePower.selectPowerIdByRoleId(it)
        }.flatten().distinct()

        val codes = powerIds.mapNotNull { powers.selectById(it)?.powerCode }

        return codes.toList()
    }

    override fun getLoginUserId(username: String): String? {
        return users.selectByUsername(username)?.userId
    }

    override fun getLoginUserById(userId: String): LoginUser {
        return SecurityUserProxy(userId, this)
    }

    @Transactional(rollbackFor = [Exception::class])
    protected fun updateLoginTime(userId: String) {
        val user = Entity.create<SysUser>().apply {
            this.userId = userId
            lastTime = LocalDateTime.now()
        }
        users.update(user)
    }

    override fun loginRecord(userId: String, description: String, success: Boolean, errorMsg: String) {
        logging.record {
            it.title = "登录"
            it.description = description
            it.businessType = BusinessType.OTHER
            it.success = success
            it.loggingType = LoggingType.LOGIN
            it.errorMsg = errorMsg
        }
        updateLoginTime(userId)
    }

    private class SecurityUserProxy(
        override val userId: String, private val service: ProjectSecurityService
    ) : LoginUser {
        private val user get() = service.getUserById(userId)
        override val password: String get() = user?.password ?: ""
        override val isAdmin: Boolean get() = user?.admin ?: false
        override val authorities: Collection<String> get() = service.getAuthorities(userId)
        override val isNonLocked: Boolean = user?.unlocked ?: false
        override val isEnable: Boolean get() = user?.enable ?: false
    }
}
