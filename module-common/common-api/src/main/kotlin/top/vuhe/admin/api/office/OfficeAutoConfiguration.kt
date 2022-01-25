package top.vuhe.admin.api.office

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
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
    @ConditionalOnMissingBean
    fun officeHandler() = OfficeHandler()
}
