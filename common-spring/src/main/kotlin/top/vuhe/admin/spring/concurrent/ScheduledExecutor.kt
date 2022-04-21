package top.vuhe.admin.spring.concurrent

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

object ScheduledExecutor : ThreadPoolTaskScheduler() {
    init {
        poolSize = 1
        setThreadNamePrefix("Spring-Scheduled-")
        isDaemon = true
    }
}
