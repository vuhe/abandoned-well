package top.vuhe.admin.spring.config

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import top.vuhe.admin.api.network.XssCleaner
import top.vuhe.admin.spring.web.handler.RepeatSubmitHandler
import top.vuhe.admin.spring.web.handler.XssCleanHandler

/**
 * ### web 配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class ServletWebConfig : WebMvcConfigurer {

    @Bean
    fun xssJacksonCustomizer() = Jackson2ObjectMapperBuilderCustomizer {
        it.deserializerByType(String::class.java, XssCleaner.json)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        // 过滤路径
        val patterns = listOf("/**")

        // xss 拦截器
        registry.addInterceptor(XssCleanHandler)
            .addPathPatterns(patterns)
            .order(Ordered.LOWEST_PRECEDENCE - 2)

        // 重复提交拦截器
        registry.addInterceptor(RepeatSubmitHandler)
            .addPathPatterns(patterns)
            .order(Ordered.LOWEST_PRECEDENCE - 1)
    }
}
