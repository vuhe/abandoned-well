package top.vuhe.admin.spring.security.exception

import org.springframework.security.core.AuthenticationException

/**
 * ### 验证码异常类
 *
 * @author vuhe
 */
internal class CaptchaException(val info: String) : AuthenticationException(info)
