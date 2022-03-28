package top.vuhe.admin.spring.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.spring.web.interceptor.XssFilterSupport
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * ### 核心配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class CoreConfiguration {
    /**
     * jackson 序列化时间
     */
    @Bean
    fun dateTime(): Module = JavaTimeModule().apply {
        addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        )
        addSerializer(
            LocalTime::class.java,
            LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss"))
        )
    }

    /**
     * xss 过滤
     */
    @Bean
    fun xssFilterRegistrationBean() = FilterRegistrationBean<XssFilterSupport>().apply {
        filter = XssFilterSupport()
        setName("xssHttpFilter")
    }
}
