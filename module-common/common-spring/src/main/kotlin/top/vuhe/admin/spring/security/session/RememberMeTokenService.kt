package top.vuhe.admin.spring.security.session

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * ### rememberMe 的 token 服务
 * 由于涉及了 spring cache 的代理，此对象由注解注入 spring
 *
 * @author vuhe
 */
@Service
class RememberMeTokenService : PersistentTokenRepository {

    /**
     * 创建 token 对象，主要包括：
     * - 调用 [cacheSeriesId] 缓存 SeriesId
     * - 调用 [cacheTokenObj] 缓存 TokenObj
     */
    override fun createNewToken(token: PersistentRememberMeToken) {
        val tokenObj = RememberMeToken().also {
            it.username = token.username
            it.token = token.tokenValue
            it.date = token.date.time
        }

        cacheSeriesId(token.username, token.series)
        cacheTokenObj(token.series, tokenObj)
    }

    /**
     * 更新 token 对象，只需要更新 TokenObj，
     * 因此只需要调用 [cacheTokenObj]
     */
    override fun updateToken(series: String, tokenValue: String, lastUsed: Date?) {
        val map: RememberMeToken? = findTokenObj(series)
        map?.let {
            it.token = tokenValue
            cacheTokenObj(series, it)
        }
    }

    /**
     * 使用缓存查找 token，
     * 如果缓存中没有直接返回 null
     */
    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? {
        val tokenObj: RememberMeToken? = findTokenObj(seriesId)
        return tokenObj?.let {
            PersistentRememberMeToken(it.username, seriesId, it.token, Date(it.date))
        }
    }

    /**
     * 此方法会先调用 [deleteTokenObj] 删除 TokenObj，
     * 再调用代理中的方法删除 SeriesId
     */
    @CacheEvict("remember-me", key = "#username")
    override fun removeUserTokens(username: String) {
        val seriesId = findSeriesId(username)
        deleteTokenObj(seriesId)
    }

    /* ----------------------------- username -> seriesId 缓存方法 -------------------------- */

    @CachePut("remember-me", key = "#username")
    fun cacheSeriesId(username: String, seriesId: String): String = seriesId

    @Cacheable("remember-me", key = "#username")
    fun findSeriesId(username: String): String = ""

    /* ----------------------------- seriesId -> tokenObj 缓存方法 -------------------------- */

    @CachePut("remember-me", key = "#seriesId")
    fun cacheTokenObj(seriesId: String, token: RememberMeToken) = token

    @Cacheable("remember-me", key = "#seriesId")
    fun findTokenObj(seriesId: String): RememberMeToken? = null

    @CacheEvict("remember-me", key = "#seriesId")
    fun deleteTokenObj(seriesId: String) {
        // spring cache will delete token obj
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
