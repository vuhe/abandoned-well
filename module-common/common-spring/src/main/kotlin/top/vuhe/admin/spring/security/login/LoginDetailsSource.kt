package top.vuhe.admin.spring.security.login

import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.web.authentication.WebAuthenticationDetails
import top.vuhe.admin.api.security.util.Captcha
import javax.servlet.http.HttpServletRequest

/**
 * 对登录信息附加验证必要字段
 *
 * @author vuhe
 */
object LoginDetailsSource : AuthenticationDetailsSource<HttpServletRequest, LoginDetailsSource.LoginParameterDetail> {
    /**
     * 本方法将请求信息附加到对象中，只提供字段，不进行实际的鉴权
     */
    override fun buildDetails(context: HttpServletRequest): LoginParameterDetail {
        // 检查验证码，并记录信息
        val captcha = context.getParameter("captcha") ?: ""
        val captchaEmpty = captcha.isEmpty()
        val captchaChecked = !captchaEmpty && Captcha.ver(captcha, context)

        // 返回登录附加信息
        return LoginParameterDetail(context, captchaEmpty, captchaChecked)
    }

    /**
     * 此对象用于携带登录时的额外信息
     *
     * @param captchaEmpty   验证码是否为空
     * @param captchaChecked 验证码验证是否通过
     */
    class LoginParameterDetail(
        context: HttpServletRequest,
        val captchaEmpty: Boolean,
        val captchaChecked: Boolean,
    ) : WebAuthenticationDetails(context)
}
