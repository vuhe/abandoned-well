package top.vuhe.admin.schedule

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import top.vuhe.admin.schedule.handler.ScheduleStarted

/**
 * ### 定时任务配置文件
 *
 * @author vuhe
 */
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(QuartzAutoConfiguration::class)
class ScheduleAutoConfiguration {
    /**
     * 用于初始化操作
     */
    @Bean
    fun jobStartedHandler() = ScheduleStarted()
}
