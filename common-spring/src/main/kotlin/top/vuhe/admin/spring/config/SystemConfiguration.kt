package top.vuhe.admin.spring.config

import com.fasterxml.jackson.databind.Module
import org.ktorm.database.Database
import org.ktorm.jackson.KtormModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.EnableTransactionManagement
import top.vuhe.admin.api.logging.LoggingAspect
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.monitor.CpuInfo
import top.vuhe.admin.spring.dsl.javaTimeModule
import top.vuhe.admin.spring.web.interceptor.XssFilterSupport
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.sql.DataSource

/**
 * ### 核心配置
 *
 * @author vuhe
 */
@EnableTransactionManagement
@Configuration(proxyBeanMethods = false)
class SystemConfiguration {

    /**
     * jackson 序列化时间
     */
    @Bean
    fun dateTime(): Module = javaTimeModule {
        LocalDateTime::class byPattern "yyyy-MM-dd HH:mm:ss"
        LocalDate::class byPattern "yyyy-MM-dd"
        LocalTime::class byPattern "HH:mm:ss"
    }

    /**
     * ktorm entity 序列化
     */
    @Bean
    fun ktormModule(): Module = KtormModule()

    /**
     * xss 过滤
     */
    @Bean
    fun xssFilter() = XssFilterSupport()

    /**
     * 日志记录
     */
    @Bean
    fun loggingAspect(factory: LoggingFactory) = LoggingAspect(factory)

    /**
     * 注册 database 作为 bean
     */
    @Bean
    fun database(dataSource: DataSource): Database {
        return Database.connectWithSpringSupport(dataSource)
    }

    /**
     * 硬件信息刷新任务
     */
    @Scheduled(fixedDelay = 60 * 1000)
    fun hardwareRefresher() = CpuInfo.updateInfo()
}
