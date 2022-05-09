package top.vuhe.admin.spring.config

import com.fasterxml.jackson.databind.Module
import org.ktorm.jackson.KtormModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.spring.dsl.DatetimeModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * ### Json 配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class JacksonConfiguration {
    /** jackson 序列化时间 */
    @Bean
    fun datetimeModule(): Module = DatetimeModule {
        LocalDateTime::class byPattern "yyyy-MM-dd HH:mm:ss"
        LocalDate::class byPattern "yyyy-MM-dd"
        LocalTime::class byPattern "HH:mm:ss"
    }

    /** ktorm entity 序列化 */
    @Bean
    fun ktormModule() = KtormModule()
}
