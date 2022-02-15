package top.vuhe.admin.spring.web.interceptor

import org.slf4j.LoggerFactory
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.api.network.isAjax
import top.vuhe.admin.spring.web.response.ResultObj
import javax.servlet.http.HttpServletRequest

/**
 * 全局异常处理类
 *
 * @author vuhe
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * 不 支 持 的 请 求 类 型
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleException(e: HttpRequestMethodNotSupportedException): ResultObj<*> {
        log.error(e.message, e)
        return ResultObj.Fail<Nothing>(message = "不支持' " + e.method + "'请求")
    }

    /**
     * 拦 截 未 知 的 运 行 时 异 常
     */
    @ExceptionHandler(RuntimeException::class)
    fun notFount(e: RuntimeException): ResultObj<*> {
        log.error("运行时异常:", e)
        return ResultObj.Fail<Nothing>(message = "运行时异常:" + e.message)
    }

    /**
     * 拦 截 参 数 验 证 失 败 异 常
     */
    @ExceptionHandler(BindException::class)
    fun validationFailed(request: HttpServletRequest, e: BindException): ResultObj<*> {
        val message = e.bindingResult.allErrors.lastOrNull()?.defaultMessage ?: "参数不符合要求"
        return ResultObj.Fail<Nothing>(message = message)
    }

    /**
     * 权 限 异 常 处 理
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun access(request: HttpServletRequest, e: AccessDeniedException): Any {
        e.printStackTrace()
        return if (request.isAjax) {
            ResultObj.Fail<Nothing>(message = "暂无权限")
        } else {
            val modelAndView = ModelAndView()
            modelAndView.addObject("errorMessage", e.message)
            modelAndView.viewName = "error/403"
            modelAndView
        }
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResultObj<*> {
        log.error(e.message, e)
        return ResultObj.Fail<Nothing>(message = "服务器错误，请联系管理员")
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException::class)
    fun businessException(request: HttpServletRequest, e: BusinessException): Any {
        log.error(e.message, e)
        return if (request.isAjax) {
            ResultObj.Fail<Nothing>(message = e.message)
        } else {
            val modelAndView = ModelAndView()
            modelAndView.addObject("errorMessage", e.message)
            modelAndView.viewName = "error/500"
            modelAndView
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
