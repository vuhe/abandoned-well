package top.vuhe.admin.system.service.impl

import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.cache.cacheGet
import top.vuhe.admin.spring.security.principal.LoginUser
import top.vuhe.admin.spring.security.principal.LoginUserAuthority
import top.vuhe.admin.spring.security.principal.UserSecurityService
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.mapper.SysRoleMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import java.time.LocalDateTime

/**
 * 提供动态用户信息，防止数据不同步
 *
 * @author vuhe
 */
@Service
class LoginUserServiceImpl(
    private val sysUserMapper: SysUserMapper,
    private val sysRoleMapper: SysRoleMapper,
    private val sysPowerMapper: SysPowerMapper
) : UserSecurityService {

    /**
     * 为减少数据库访问，此方法会缓存数据，在数据库发生数据更改时，
     * 会删除数据，此时会在此缓存
     */
    fun getUserById(id: String): SysUser? = sysUserMapper.selectById(id)

    /**
     * 此方法会缓存用户权限列表，出现权限变化时会清空缓存
     */
    fun getAuthorities(userId: String): Set<GrantedAuthority> {
        // 转换为 roleId
        val roleIds = sysUserMapper.selectRoleIdByUserId(userId)

        // 转换为 powerId
        val powerIds = roleIds.map {
            sysRoleMapper.selectPowerIdByRoleId(it)
        }.flatten()

        // 查询 power 权限，加入列表
        val powerList = sysPowerMapper.selectListByIds(powerIds).asSequence().map {
            LoginUserAuthority(it.powerCode)
        }.toSet()

        // 缓存后返回
        cacheGet("authority", key = userId) { powerList }
        return powerList
    }

    override fun getLoginUserId(username: String): String? {
        return sysUserMapper.selectByUsername(username)?.userId
    }

    override fun getLoginUserById(userId: String): LoginUser {
        return createLoginUser(userId)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateLoginTime(userId: String) {
        val user = sysUserMapper.selectById(userId) ?: return
        user.lastTime = LocalDateTime.now()
        sysUserMapper.update(user)
    }

    private fun createLoginUser(userId: String) = object : LoginUser {
        private val service = this@LoginUserServiceImpl
        private val user get() = service.getUserById(userId)
        private var erased: Boolean = false
        override val userId: String = userId
        override val password: String get() = if (erased) "" else user?.password ?: ""
        override val nickname: String get() = user?.username ?: ""
        override val isAdmin: Boolean get() = user?.admin ?: false
        override val authorities: Set<GrantedAuthority>
            get() = service.getAuthorities(userId)
        override val isNonLocked: Boolean = user?.unlocked ?: false
        override val isEnable: Boolean
            get() = user?.enable ?: false

        override fun eraseCredentials() {
            erased = true
        }
    }
}
