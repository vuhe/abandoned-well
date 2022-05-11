package top.vuhe.admin.spring.web.handler

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.api.network.XssCleaner
import top.vuhe.admin.api.network.isAjax
import top.vuhe.admin.spring.web.response.AjaxCode
import top.vuhe.admin.spring.web.response.AjaxResult
import javax.servlet.http.HttpServletRequest

/**
 * ## 全局请求处理类
 *
 * @author vuhe
 */
@RestControllerAdvice
class GlobalAdviceHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    /** 处理参数 xss */
    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        // 处理前端传来的表单字符串
        binder.registerCustomEditor(String::class.java, XssCleaner.form)
    }

    /** 不支持的请求类型 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleException(e: HttpRequestMethodNotSupportedException): AjaxResult {
        log.error(e.message, e)
        return AjaxResult.fail(message = "不支持 '${e.method}'请求")
    }

    /** 拦截参数验证失败异常 */
    @ExceptionHandler(BindException::class)
    fun validationFailed(e: BindException): AjaxResult {
        val message = e.bindingResult.allErrors.lastOrNull()?.defaultMessage ?: "参数不符合要求"
        return AjaxResult.fail(message = message)
    }

    /** 权限异常处理 */
    @ExceptionHandler(AccessDeniedException::class)
    fun access(request: HttpServletRequest, e: AccessDeniedException): Any {
        return if (request.isAjax) {
            AjaxResult.fail(type = AjaxCode.AccessDenied)
        } else {
            ModelAndView("error/403")
        }
    }

    /** 数据完整性异常 */
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun dataIntegrityViolationException(request: HttpServletRequest): Any {
        return if (request.isAjax) {
            AjaxResult.fail(message = "违反数据约束，不能执行此操作！")
        } else {
            ModelAndView("error/500")
        }
    }

    /** 业务异常，此异常应为逻辑异常，不需要排查 */
    @ExceptionHandler(BusinessException::class)
    fun businessException(request: HttpServletRequest, e: BusinessException): Any {
        return if (request.isAjax) {
            AjaxResult.fail(message = e.message)
        } else {
            ModelAndView("error/500")
        }
    }

    /** 运行时异常 */
    @ExceptionHandler(RuntimeException::class)
    fun notFount(e: RuntimeException): AjaxResult {
        log.error("运行时异常:", e)
        return AjaxResult.fail(message = "运行时异常: ${e.message}")
    }

    /** 其他异常 */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): AjaxResult {
        log.error(e.message, e)
        return AjaxResult.fail(message = "服务器错误，请联系管理员")
    }
}
