package top.vuhe.config

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import top.vuhe.thread.ParallelExecutor
import top.vuhe.thread.ScheduledExecutor
import top.vuhe.thread.SerialExecutor

/**
 * ### 异步配置
 *
 * @author vuhe
 */
@EnableAsync
@EnableScheduling
@Configuration
class AsyncConfig : AsyncConfigurer, SchedulingConfigurer {
    private val log = LoggerFactory.getLogger("AsyncThreadLog")

    @Bean(destroyMethod = "shutdown")
    fun scheduledExecutor() = ScheduledExecutor

    @Bean("serialExecutor", destroyMethod = "shutdown")
    fun serialExecutor() = SerialExecutor()

    @Bean("defaultExecutor", destroyMethod = "shutdown")
    override fun getAsyncExecutor() = ParallelExecutor

    override fun getAsyncUncaughtExceptionHandler() =
        AsyncUncaughtExceptionHandler { ex, method, params: Array<*>? ->
            val param = params?.joinToString() ?: ""
            log.warn("执行异步任务 $method ($param)", ex)
        }

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setTaskScheduler(scheduledExecutor())
    }
}
