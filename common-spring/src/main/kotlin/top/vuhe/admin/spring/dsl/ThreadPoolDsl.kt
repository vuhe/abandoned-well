package top.vuhe.admin.spring.dsl

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import top.vuhe.admin.api.annotation.ProjectDsl
import java.util.concurrent.Executor

inline fun threadPool(block: ThreadPoolDsl.() -> Unit): Executor {
    return ThreadPoolDsl().apply(block).build()
}

@ProjectDsl
@Suppress("MemberVisibilityCanBePrivate")
class ThreadPoolDsl {
    /** 核心线程池大小, 默认为Java虚拟机可用的处理器数 */
    var corePoolSize = Runtime.getRuntime().availableProcessors()

    /** 最大线程数 */
    var maxPoolSize = 100

    /** 队列容量 */
    var queueCapacity = 200

    /** 线程池中线程最大空闲时间 */
    var keepAliveSeconds = 60

    /** 线程名字前缀 */
    var threadNamePrefix = "Async-ThreadPool-"

    /** 核心线程是否允许超时 */
    var allowCoreThreadTimeOut = false

    /** IOC容器关闭时是否阻塞等待剩余的任务执行完成 */
    var waitForTasksToCompleteOnShutdown = true

    /** 阻塞IOC容器关闭的时间 */
    var awaitTerminationSeconds = 10

    fun build(): Executor = ThreadPoolTaskExecutor().also {
        it.corePoolSize = corePoolSize
        it.maxPoolSize = maxPoolSize
        it.setQueueCapacity(queueCapacity)
        it.keepAliveSeconds = keepAliveSeconds
        it.setThreadNamePrefix(threadNamePrefix)
        it.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut)
        it.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown)
        it.setAwaitTerminationSeconds(awaitTerminationSeconds)
    }
}
