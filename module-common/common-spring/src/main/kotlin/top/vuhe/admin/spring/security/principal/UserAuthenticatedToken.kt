package top.vuhe.admin.spring.security.principal

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * ## 已登陆的用户授权信息
 *
 * @author vuhe
 */
class UserAuthenticatedToken(
    private val user: LoginUser, password: Any, details: Any
) : UsernamePasswordAuthenticationToken(user.userId, password, emptyList()) {
    init {
        setDetails(details)
    }

    /**
     * 此函数会被鉴权系统调用
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val set = user.authorities.asSequence()
            .distinct()
            .map { AuthorityCode(it) }
            .toMutableSet()
        if (user.isAdmin) {
            set.add(AuthorityCode("ROLE_ADMIN"))
        }
        return set
    }

    /**
     * 实现系统权限代码转换
     */
    data class AuthorityCode(private val code: String) : GrantedAuthority {
        override fun getAuthority(): String = code
    }
}
