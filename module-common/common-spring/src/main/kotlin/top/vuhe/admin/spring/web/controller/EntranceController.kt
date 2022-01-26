package top.vuhe.admin.spring.web.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.logging.Logging
import top.vuhe.admin.api.security.Captcha
import top.vuhe.admin.spring.security.principal.LoginUserInfo.isAuthentication
import top.vuhe.admin.spring.security.session.SecuritySessionRegistry
import top.vuhe.admin.spring.web.response.ResultObj
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 入口控制器
 *
 * @author vuhe
 */
@RestController
@RequestMapping
@Tag(name = "项目入口")
class EntranceController(
    private val sessionRegistry: SecuritySessionRegistry
) : BaseController() {
    /**
     *  获取登录视图
     * @return  登录视图
     */
    @GetMapping("login")
    fun login(request: HttpServletRequest) = if (isAuthentication) {
        sessionRegistry.expiredSession(request.session)
        ModelAndView("index")
    } else ModelAndView("login")

    /**
     * 获取主页视图
     * @return  登录视图
     */
    @GetMapping("index")
    @Logging("主页", describe = "返回 Index 主页视图", type = BusinessType.ADD)
    fun index() = ModelAndView("index")

    /**
     *  获取主页视图
     * Param: ModelAndView
     * @return  主页视图
     */
    @GetMapping("console")
    fun home() = ModelAndView("console/console")

    /**
     * 无权限页面
     * @return 返回403页面
     */
    @GetMapping("error/403")
    fun noPermission() = ModelAndView("error/403")

    /**
     * 找不带页面
     * @return 返回404页面
     */
    @GetMapping("error/404")
    fun notFound() = ModelAndView("error/404")

    /**
     * 找不带页面
     * @return 返回404页面
     */
    @GetMapping("error/not_available")
    fun notAvailable() = ModelAndView("error/not_available")

    /**
     * 异常处理页
     * @return 返回500界面
     */
    @GetMapping("error/500")
    fun onException() = ModelAndView("error/500")

    /**
     * 验证码生成
     *
     * @param request  请求报文
     * @param response 响应报文
     */
    @RequestMapping("system/captcha/generate")
    fun generate(request: HttpServletRequest, response: HttpServletResponse) {
        Captcha.out(request, response)
    }

    /**
     * 异步验证
     *
     * @param request 请求报文
     * @param captcha 验证码
     * @return 验证结果
     */
    @RequestMapping("system/captcha/verify")
    fun verify(request: HttpServletRequest, captcha: String): ResultObj<*> {
        return if (Captcha.ver(captcha, request)) {
            ResultObj.Success<Nothing>(message = "验证成功")
        } else ResultObj.Fail<Nothing>(message = "验证失败")
    }
}
