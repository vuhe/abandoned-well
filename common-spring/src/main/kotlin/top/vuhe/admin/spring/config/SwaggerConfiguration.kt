package top.vuhe.admin.spring.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

/**
 * ### 接口文档配置文件
 *
 * @author vuhe
 */
@Configuration
@ConditionalOnClass(GroupedOpenApi::class)
class SwaggerConfiguration {
    @PostConstruct
    @Suppress("SpellCheckingInspection")
    fun setProperties() {
        System.setProperty("springdoc.packages-to-scan", "top.vuhe")
    }

    @Bean
    fun apiInfo() = OpenAPI().apply {
        info = Info().apply {
            title("井管理系统 API")
            description("废弃井管理系统")
            version("Release 1.0.0")
            termsOfService("https://github.com/vuhe")
            contact(Contact().apply {
                name = "vuhe"
                url = "https://github.com/vuhe"
                email = "zhuhe202@qq.com"
            })
            license = License()
                .name("MIT")
                .url("https://github.com/vuhe/AdminTemplate/blob/main/LICENSE")
        }
    }
}
