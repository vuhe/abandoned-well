package top.vuhe.admin.api.cache.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import top.vuhe.admin.api.cache.TimeoutCacheProperties
import top.vuhe.admin.api.cache.manager.RedisCacheManager
import top.vuhe.admin.api.cache.manager.TimeoutCacheManager

/**
 * ## redis 缓存的配置
 * 查找 [RedisAutoConfiguration] 路径是否存在，判断使用类型
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations::class)
@ConditionalOnMissingBean(TimeoutCacheManager::class)
@Import(RedisAutoConfiguration::class, JacksonAutoConfiguration::class)
@AutoConfigureAfter(RedisAutoConfiguration::class, JacksonAutoConfiguration::class)
class RedisCacheAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun redisCacheManager(
        cacheProperties: TimeoutCacheProperties,
        mapper: ObjectMapper, redisConnectionFactory: RedisConnectionFactory
    ): TimeoutCacheManager {
        log.info("Found redis cache, use redis timeout cache!")
        val objectRedisTemplate = objectRedisTemplate(mapper, redisConnectionFactory)
        return RedisCacheManager(cacheProperties, objectRedisTemplate)
    }

    private fun objectRedisTemplate(mapper: ObjectMapper, redisConnectionFactory: RedisConnectionFactory) =
        RedisTemplate<String, Any>().apply {
            val objSerializer = Jackson2JsonRedisSerializer(Any::class.java).apply {
                // 系统部分框架以及 kotlin 的序列化需要容器中的 mapper 支持
                setObjectMapper(mapper)
            }
            setConnectionFactory(redisConnectionFactory)
            keySerializer = StringRedisSerializer()
            valueSerializer = objSerializer
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = objSerializer
            afterPropertiesSet()
        }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
