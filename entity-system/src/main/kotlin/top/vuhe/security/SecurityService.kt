package top.vuhe.security

import org.ktorm.entity.Entity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.domain.power.SysPowerRepository
import top.vuhe.domain.user.SysUser
import top.vuhe.domain.user.SysUserRepository
import top.vuhe.logging.BusinessType
import top.vuhe.logging.LoggingFactory
import top.vuhe.logging.LoggingType
import top.vuhe.mapper.LinkRolePower
import top.vuhe.mapper.LinkUserRole
import top.vuhe.security.principal.LoginUser
import java.time.LocalDateTime

/**
 * 提供动态用户信息，防止数据不同步
 *
 * @author vuhe
 */
@Service
class SecurityService(
    private val logging: LoggingFactory,
    private val users: SysUserRepository,
    private val userRole: LinkUserRole,
    private val rolePower: LinkRolePower,
    private val powers: SysPowerRepository
) : SpringSecurityService {

    fun getUserById(id: String): SysUser? = users.selectById(id)

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

    override fun buildLoginUser(username: String): LoginUser? {
        val userId = users.selectByUsername(username)?.userId
        return userId?.let { id -> SecurityUserProxy(id, this) }
    }

    @Transactional(rollbackFor = [Exception::class])
    protected fun updateLoginTime(userId: String) {
        val user = Entity.create<SysUser>().apply {
            this.userId = userId
            lastTime = LocalDateTime.now()
        }
        users.update(user)
    }

    override fun loginSuccess(userId: String, description: String) {
        logging.record {
            it.title = "登录"
            it.description = description
            it.businessType = BusinessType.OTHER
            it.success = true
            it.loggingType = LoggingType.LOGIN
        }
        updateLoginTime(userId)
    }

    override fun loginFail(description: String, errorMsg: String) {
        logging.record {
            it.title = "登录"
            it.description = description
            it.businessType = BusinessType.OTHER
            it.success = false
            it.loggingType = LoggingType.LOGIN
            it.errorMsg = errorMsg
        }
    }

    private class SecurityUserProxy(
        override val userId: String, private val service: SecurityService
    ) : LoginUser {
        private val user get() = service.getUserById(userId)
        override val password: String get() = user?.password ?: ""
        override val isAdmin: Boolean get() = user?.admin ?: false
        override val authorities: Collection<String> get() = service.getAuthorities(userId)
        override val isNonLocked: Boolean = user?.unlocked ?: false
        override val isEnable: Boolean get() = user?.enable ?: false
    }
}
