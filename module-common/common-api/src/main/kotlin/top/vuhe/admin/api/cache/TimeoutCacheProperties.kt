package top.vuhe.admin.api.cache

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * 有过期时间缓存配置
 *
 * @author vuhe
 */
@ConfigurationProperties(prefix = "project.cache")
object TimeoutCacheProperties {
    /**
     * 初始化默认缓存
     */
    var cacheNames: List<String> = emptyList()

    /**
     * 过期时间，默认 10 分钟
     */
    var timeout: Duration = Duration.ofMinutes(10)

    /**
     * 是否允许 null 值，默认不允许
     */
    var isCacheNullValues: Boolean = false

    /**
     * redis 缓存设置
     */
    val redis = Redis

    /**
     * redis 缓存设置
     */
    object Redis {
        /**
         * 是否使用前缀
         */
        var isUseKeyPrefix: Boolean = true

        /**
         * 默认前缀
         */
        var keyPrefix: String = "admin-template"
    }
}
