package top.vuhe.security.principal

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * ## 已登陆的用户授权信息
 *
 * @author vuhe
 */
class UserLoggedToken(
    private val user: LoginUser, password: Any, details: Any
) : UsernamePasswordAuthenticationToken(user.userId, password, emptyList()) {
    init {
        setDetails(details)
    }

    /**
     * 此函数会被鉴权系统调用
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val list = user.authorities.map { SimpleGrantedAuthority(it) }
        return if (user.isAdmin) list + admin
        else list
    }

    companion object {
        private val admin = listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
    }
}
