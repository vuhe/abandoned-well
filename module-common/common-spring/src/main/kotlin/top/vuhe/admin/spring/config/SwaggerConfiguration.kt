package top.vuhe.admin.spring.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.spring.property.SwaggerProperty
import javax.annotation.PostConstruct

/**
 * ### 接口文档配置文件
 *
 * @author vuhe
 */
@Configuration
@ConditionalOnClass(GroupedOpenApi::class)
@EnableConfigurationProperties(SwaggerProperty::class)
class SwaggerConfiguration {
    @PostConstruct
    @Suppress("SpellCheckingInspection")
    fun setProperties() {
        System.setProperty("springdoc.packages-to-scan", "top.vuhe")
    }

    @Bean
    fun apiInfo(documentInfo: SwaggerProperty) = OpenAPI().apply {
        info = Info().apply {
            title(documentInfo.title)
            description(documentInfo.describe)
            version(documentInfo.version)
            termsOfService(documentInfo.termsOfServiceUrl)
            contact(documentInfo.contact)
            license = License()
                .name(documentInfo.licence)
                .url(documentInfo.licenceUrl)
        }
    }
}
