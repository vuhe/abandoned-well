package top.vuhe.admin.api.exception

import org.springframework.security.core.AuthenticationException

/**
 * ### 验证码异常类
 *
 * @author vuhe
 */
class CaptchaException(val info: String) :
    AuthenticationException(info)
