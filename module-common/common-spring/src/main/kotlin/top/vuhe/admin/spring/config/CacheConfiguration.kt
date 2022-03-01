package top.vuhe.admin.spring.config

import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import top.vuhe.admin.api.cache.TimeoutCacheAutoConfiguration
import top.vuhe.admin.api.cache.manager.TimeoutCacheManager

/**
 * 缓存支持配置类
 *
 * @author vuhe
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(TimeoutCacheAutoConfiguration::class)
class CacheConfiguration {
    /**
     * 使用提供过期时间的缓存
     */
    @Bean
    @Primary
    fun timoutCacheManager(cacheManager: TimeoutCacheManager): CacheManager = cacheManager
}
