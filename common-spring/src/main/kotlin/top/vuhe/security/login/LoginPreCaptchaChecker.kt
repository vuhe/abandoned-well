package top.vuhe.security.login

import com.wf.captcha.utils.CaptchaUtil
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import javax.servlet.http.HttpServletRequest

/**
 * ### 验证码检查
 *
 * @author vuhe
 */
object LoginPreCaptchaChecker : WebAuthenticationDetailsSource() {
    override fun buildDetails(context: HttpServletRequest): WebAuthenticationDetails {
        val captcha = context.getParameter("captcha") ?: ""

        // 检查验证码
        captcha.ifEmpty { throw CaptchaEmptyException }
        if (!CaptchaUtil.ver(captcha, context)) {
            throw CaptchaMatchException
        }

        return super.buildDetails(context)
    }

    private object CaptchaEmptyException : AuthenticationException("验证码不能为空!")
    private object CaptchaMatchException : AuthenticationException("验证码错误!")
}
