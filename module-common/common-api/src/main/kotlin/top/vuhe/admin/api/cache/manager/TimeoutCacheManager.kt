package top.vuhe.admin.api.cache.manager

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import top.vuhe.admin.api.cache.group.TimeoutCache
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

/**
 * 带有过期时间的缓存
 *
 * @author vuhe
 */
abstract class TimeoutCacheManager(
    initNames: List<String>,
    protected val defaultTimeout: Duration
) : CacheManager {
    protected val splitFlag = "#"

    /**
     * 缓存列表
     */
    protected val cacheNames = ConcurrentHashMap<String, TimeoutCache>().apply {
        initNames.forEach { put(it, createCache(it, defaultTimeout)) }
    }

    /**
     * 获取一个默认缓存，默认为 10 分钟
     *
     * @param name 缓存名称
     */
    override fun getCache(name: String): Cache {
        return if (name.contains(splitFlag)) {
            val param = name.split(splitFlag)
            try {
                // 数组可能越界
                val timout = getTimeout(param[1])
                getCache(param[0], timout)
            } catch (_: Exception) {
                // 数组越界使用默认值
                getCache(name, defaultTimeout)
            }
            // 无分隔符时，使用默认值
        } else getCache(name, defaultTimeout)
    }

    /**
     * 获取一个缓存，需要指定时间
     *
     * @param name    缓存名称
     * @param timeout 过期时间
     */
    fun getCache(name: String, timeout: Duration): Cache {
        val cache = cacheNames[name]
        return if (cache == null) {
            val c = createCache(name, timeout)
            cacheNames[name] = c
            c
        } else cache
    }

    /**
     * 获取全部缓存名称
     */
    override fun getCacheNames(): Collection<String> {
        return cacheNames.map { it.key }
    }

    /**
     * 创建一个新缓存
     */
    protected abstract fun createCache(name: String, timeout: Duration): TimeoutCache

    /**
     * 将字符串转换为 Duration
     */
    private fun getTimeout(str: String): Duration {
        return try {
            Duration.parse(str)
        } catch (_: Exception) {
            log.warn("Duration 字符串 $str 解析错误，使用默认值 10 分钟！")
            defaultTimeout
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
