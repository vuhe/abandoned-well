package top.vuhe.admin.spring.config

import com.fasterxml.jackson.databind.Module
import org.ktorm.jackson.KtormModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import top.vuhe.admin.api.jackson.DatetimeModule
import top.vuhe.admin.api.jackson.LanguageModule

/**
 * ## Json 配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class JacksonConfig {

    @Bean
    fun jacksonModuleCustomize(modules: List<Module>) = JacksonModuleCustomizer(modules)

    class JacksonModuleCustomizer(
        private val modules: List<Module>
    ) : Jackson2ObjectMapperBuilderCustomizer, Ordered {
        override fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder) {
            val knownModules = listOf(DatetimeModule, LanguageModule, KtormModule())
            jacksonObjectMapperBuilder.modules(modules + knownModules)
        }

        override fun getOrder(): Int = Int.MAX_VALUE
    }
}
