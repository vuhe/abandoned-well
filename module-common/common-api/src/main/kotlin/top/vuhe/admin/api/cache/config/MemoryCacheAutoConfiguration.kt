package top.vuhe.admin.api.cache.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.api.cache.TimeoutCacheProperties
import top.vuhe.admin.api.cache.manager.MemoryCacheManager
import top.vuhe.admin.api.cache.manager.TimeoutCacheManager

/**
 * ## 内存缓存的配置
 * 如果其它缓存未提供，则由内存提供缓存支持
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(TimeoutCacheManager::class)
class MemoryCacheAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun memoryCacheManager(cacheProperties: TimeoutCacheProperties): TimeoutCacheManager {
        log.info("Can't find other TimeoutCache, use default memory timeout cache!")
        return MemoryCacheManager(cacheProperties)
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
