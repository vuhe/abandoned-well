package top.vuhe.thread

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

object ParallelExecutor : ThreadPoolTaskExecutor() {
    init {
        // 核心线程池
        corePoolSize = Runtime.getRuntime().availableProcessors()
        // 最大线程数
        maxPoolSize = 100
        // 队列最大长度，防止 OOM
        setQueueCapacity(200)
        // 线程池中线程最大空闲时间，不空闲
        keepAliveSeconds = 60
        // 线程名字前缀
        setThreadNamePrefix("Parallel-ThreadPool-")
        // 核心线程是否允许超时
        setAllowCoreThreadTimeOut(false)
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成
        setWaitForTasksToCompleteOnShutdown(true)
        // 阻塞IOC容器关闭的时间
        setAwaitTerminationSeconds(10)
    }
}
