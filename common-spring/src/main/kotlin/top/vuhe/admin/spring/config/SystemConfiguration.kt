package top.vuhe.admin.spring.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.ktorm.database.Database
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.api.jackson.addSerializer
import top.vuhe.admin.api.logging.LoggingAspect
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.monitor.MonitorInfo
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
@Configuration(proxyBeanMethods = false)
class SystemConfiguration(mapper: ObjectMapper) {

    init {
        objectMapper = mapper
    }

    /**
     * jackson 序列化时间
     */
    @Bean
    fun dateTime(): Module = JavaTimeModule().apply {
        addSerializer<LocalDateTime>("yyyy-MM-dd HH:mm:ss")
        addSerializer<LocalDate>("yyyy-MM-dd")
        addSerializer<LocalTime>("HH:mm:ss")
    }

    /**
     * xss 过滤
     */
    @Bean
    fun xssFilterRegistrationBean() = FilterRegistrationBean<XssFilterSupport>().apply {
        filter = XssFilterSupport()
        setName("xssHttpFilter")
    }

    /**
     * 日志记录
     */
    @Bean
    fun loggingAspect(loggingFactory: LoggingFactory) = LoggingAspect(loggingFactory)

    /**
     * 注册 database 作为 bean
     */
    @Bean
    fun database(dataSource: DataSource): Database {
        database = Database.connectWithSpringSupport(dataSource)
        return database
    }

    /**
     * 硬件信息刷新任务
     */
    @Bean
    fun hardwareRefresher() = CommandLineRunner { MonitorInfo.refresh() }

    companion object {
        internal lateinit var database: Database
        internal lateinit var objectMapper: ObjectMapper
    }
}
