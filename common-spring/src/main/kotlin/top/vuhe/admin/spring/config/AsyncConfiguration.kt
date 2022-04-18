package top.vuhe.admin.spring.config

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import top.vuhe.admin.spring.dsl.threadPool
import java.util.concurrent.Executor

/**
 * ### 异步配置
 *
 * @author vuhe
 */
@EnableAsync
@Configuration
class AsyncConfiguration : AsyncConfigurer {
    private val log = LoggerFactory.getLogger("AsyncThreadLog")

    @Bean
    fun asyncExecutor() = threadPool {}

    override fun getAsyncExecutor(): Executor = asyncExecutor()
    override fun getAsyncUncaughtExceptionHandler() =
        AsyncUncaughtExceptionHandler { ex, method, params: Array<*>? ->
            val param = params?.joinToString() ?: ""
            log.warn("执行异步任务 $method ($param)", ex)
        }
}
