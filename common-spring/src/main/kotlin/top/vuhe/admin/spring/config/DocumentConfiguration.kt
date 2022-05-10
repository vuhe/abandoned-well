package top.vuhe.admin.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.spring.dsl.openApi
import javax.annotation.PostConstruct

/**
 * ### 接口文档配置文件
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class DocumentConfiguration {
    @PostConstruct
    @Suppress("SpellCheckingInspection")
    fun setProperties() {
        System.setProperty("springdoc.packages-to-scan", "top.vuhe.admin")
    }

    @Bean
    fun apiInfo() = openApi {
        info {
            title = "井管理系统 API"
            description = "废弃井管理系统"
            version = "Release 1.0.0"
            termsOfService = "https://gitee.com/vuhe"
            contact {
                name = "vuhe"
                url = "https://gitee.com/vuhe"
                email = "zhuhe202@qq.com"
            }
            license {
                name = "MIT"
                url = "https://github.com/vuhe/AdminTemplate/blob/main/LICENSE"
            }
        }
    }
}
