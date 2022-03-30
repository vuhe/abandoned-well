package top.vuhe.admin.spring.security.session

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import top.vuhe.admin.api.cache.cacheDelete
import top.vuhe.admin.api.cache.cacheGet
import top.vuhe.admin.api.cache.cachePut

/**
 * ### rememberMe 的 token 服务
 * 由于涉及了 spring cache 的代理，此对象由注解注入 spring
 *
 * @author vuhe
 */
object RememberMeTokenService : PersistentTokenRepository {

    /**
     * 创建 token 对象，主要包括：
     * - 缓存 SeriesId
     * - 缓存 TokenObj
     */
    override fun createNewToken(token: PersistentRememberMeToken) {
        val tokenObj = RememberMeToken().also {
            it.username = token.username
            it.token = token.tokenValue
            it.date = token.date.time
        }

        cachePut("remember-me", key = token.username, value = token.series)
        cachePut("remember-me", key = token.series, value = tokenObj)
    }

    /**
     * 更新 token 对象，只需要更新 TokenObj
     */
    override fun updateToken(series: String, tokenValue: String, lastUsed: java.util.Date?) {
        // 获取缓存
        cacheGet<RememberMeToken>("remember-me", key = series)?.let {
            // 存在缓存则更新设置
            it.token = tokenValue
            cachePut("remember-me", key = series, value = it)
        }
    }

    /**
     * 使用缓存查找 token，
     * 如果缓存中没有直接返回 null
     */
    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? {
        val tokenObj: RememberMeToken? = cacheGet("remember-me", key = seriesId)
        return tokenObj?.let {
            PersistentRememberMeToken(it.username, seriesId, it.token, java.util.Date(it.date))
        }
    }

    /**
     * 此方法会先删除 TokenObj，再删除 SeriesId
     */
    override fun removeUserTokens(username: String) {
        // 查找 seriesId 缓存
        val seriesId: String? = cacheGet("remember-me", key = username)
        // 删除缓存
        seriesId?.let { cacheDelete("remember-me", key = seriesId) }
        cacheDelete("remember-me", key = username)
    }

    /**
     * remember me token，
     * 此对象由于会放入缓存中，因此定义为 Bean 标准格式
     */
    class RememberMeToken {
        var username: String = ""
        var token: String = ""
        var date: Long = -1
    }
}
