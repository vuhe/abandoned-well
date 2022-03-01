package top.vuhe.admin.api.office

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * office 处理的自动配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class OfficeAutoConfiguration {
    @Bean
    fun officeHandler() = OfficeHandler
}
