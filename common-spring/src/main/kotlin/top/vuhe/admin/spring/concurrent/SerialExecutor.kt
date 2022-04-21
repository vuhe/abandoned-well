package top.vuhe.admin.spring.concurrent

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

class SerialExecutor : ThreadPoolTaskExecutor() {
    init {
        // 核心线程池
        corePoolSize = 1
        // 最大线程数
        maxPoolSize = 1
        // 队列最大长度，防止 OOM
        setQueueCapacity(100)
        // 线程池中线程最大空闲时间，不空闲
        keepAliveSeconds = 0
        // 线程名字前缀
        setThreadNamePrefix("Serial-ThreadPool-")
        // 核心线程是否允许超时
        setAllowCoreThreadTimeOut(false)
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成
        setWaitForTasksToCompleteOnShutdown(false)
        // 阻塞IOC容器关闭的时间
        setAwaitTerminationSeconds(0)
    }
}
