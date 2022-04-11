package top.vuhe.admin.spring.web.interceptor

import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
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
     * 不支持的请求类型
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleException(e: HttpRequestMethodNotSupportedException): ResultObj {
        log.error(e.message, e)
        return ResultObj.Fail(message = "不支持 '${e.method}'请求")
    }

    /**
     * 拦截参数验证失败异常
     */
    @ExceptionHandler(BindException::class)
    fun validationFailed(request: HttpServletRequest, e: BindException): ResultObj {
        val message = e.bindingResult.allErrors.lastOrNull()?.defaultMessage ?: "参数不符合要求"
        return ResultObj.Fail(message = message)
    }

    /**
     * 权限异常处理
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun access(request: HttpServletRequest, e: AccessDeniedException): Any {
        return if (request.isAjax) {
            ResultObj.Fail(message = "暂无权限")
        } else {
            ModelAndView("error/403").apply {
                addObject("errorMessage", e.message)
            }
        }
    }

    /**
     * 业务异常，此异常应为逻辑异常，不需要排查
     */
    @ExceptionHandler(BusinessException::class)
    fun businessException(request: HttpServletRequest, e: BusinessException): Any {
        return if (request.isAjax) {
            ResultObj.Fail(message = e.message)
        } else {
            ModelAndView("error/500").apply {
                addObject("errorMessage", e.message)
            }
        }
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException::class)
    fun notFount(e: RuntimeException): ResultObj {
        log.error("运行时异常:", e)
        return ResultObj.Fail(message = "运行时异常: ${e.message}")
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResultObj {
        log.error(e.message, e)
        return ResultObj.Fail(message = "服务器错误，请联系管理员")
    }

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}
