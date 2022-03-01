package top.vuhe.admin.api.logging

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.vuhe.admin.api.enums.LoggingType

/**
 * ### 日志切面实现
 *
 * @author vuhe
 */
@Aspect
@Component
@Suppress("unused")
class LoggingAspect @Autowired constructor(
    private val loggingFactory: LoggingFactory
) {
    @Pointcut("@annotation(top.vuhe.admin.api.logging.Logging)")
    fun dsPointCut() = Unit

    @AfterReturning(pointcut = "dsPointCut()")
    fun afterReturning(joinPoint: JoinPoint) {
        val logging = joinPoint.logging ?: return
        loggingFactory.record {
            it.title = logging.value
            it.description = logging.describe
            it.businessType = logging.type
            it.loggingType = LoggingType.OPERATE
            it.success = true
        }
    }

    @AfterThrowing(pointcut = "dsPointCut()", throwing = "e")
    fun afterThrowing(joinPoint: JoinPoint, e: Throwable) {
        val logging = joinPoint.logging ?: return
        loggingFactory.record {
            it.title = logging.value
            it.description = logging.describe
            it.businessType = logging.type
            it.loggingType = LoggingType.OPERATE
            it.success = false
            it.errorMsg = e.message ?: ""
        }
    }

    private val JoinPoint.logging: Logging?
        get() {
            val signature = signature as MethodSignature
            val method = signature.method
            return method.getAnnotation(Logging::class.java)
        }
}
