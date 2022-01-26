package top.vuhe.admin.api.security

import com.wf.captcha.utils.CaptchaUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 验证码工具
 *
 * @author vuhe
 */
object Captcha {
    fun out(request: HttpServletRequest, response: HttpServletResponse) {
        CaptchaUtil.out(request, response)
    }

    fun ver(code: String, request: HttpServletRequest): Boolean {
        return CaptchaUtil.ver(code, request)
    }
}
