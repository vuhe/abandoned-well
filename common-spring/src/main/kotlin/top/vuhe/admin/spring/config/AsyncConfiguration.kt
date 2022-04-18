package top.vuhe.admin.spring.config

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
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

    // Java虚拟机可用的处理器数
    private val processors by lazy { Runtime.getRuntime().availableProcessors() }

    @Bean
    fun asyncExecutor() = ThreadPoolTaskExecutor().apply {
        //核心线程池大小
        corePoolSize = processors
        // 最大线程数
        maxPoolSize = 100
        // 队列容量
        setQueueCapacity(200)
        // 线程池中线程最大空闲时间
        keepAliveSeconds = 60
        // 线程名字前缀
        setThreadNamePrefix("Async-ThreadPool-")
        // 核心线程是否允许超时
        setAllowCoreThreadTimeOut(false)
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成
        setWaitForTasksToCompleteOnShutdown(true)
        // 阻塞IOC容器关闭的时间
        setAwaitTerminationSeconds(10)
    }

    override fun getAsyncExecutor(): Executor = asyncExecutor()
    override fun getAsyncUncaughtExceptionHandler() =
        AsyncUncaughtExceptionHandler { ex, method, params: Array<*>? ->
            val param = params?.joinToString() ?: ""
            log.warn("执行异步任务 $method ($param)", ex)
        }
}
