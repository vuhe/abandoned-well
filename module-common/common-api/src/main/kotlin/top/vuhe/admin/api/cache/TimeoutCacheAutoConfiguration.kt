package top.vuhe.admin.api.cache

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

/**
 * 缓存配置
 *
 * @author vuhe
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TimeoutCacheProperties::class)
class TimeoutCacheAutoConfiguration {
}
