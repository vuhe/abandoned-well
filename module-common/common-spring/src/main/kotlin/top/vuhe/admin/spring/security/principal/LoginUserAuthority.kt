package top.vuhe.admin.spring.security.principal

import org.springframework.security.core.GrantedAuthority

data class LoginUserAuthority(private val code: String): GrantedAuthority {
    override fun getAuthority(): String = code
}
