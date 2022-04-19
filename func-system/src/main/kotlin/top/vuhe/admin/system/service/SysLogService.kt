package top.vuhe.admin.system.service

import org.ktorm.entity.Entity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.api.network.requestContext
import top.vuhe.admin.api.network.userAgent
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.param.SysLogParam
import top.vuhe.admin.system.repository.SysLogRepository
import top.vuhe.admin.system.repository.SysUserRepository
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * 日志服务接口实现
 *
 * @author vuhe
 */
@Service
class SysLogService(
    private val sysUserRepository: SysUserRepository,
    private val sysLogRepository: SysLogRepository
) : LoggingFactory {
    private val request by requestContext()

    override fun record(setting: (LogRecord) -> Unit) {
        // 在进入其他线程前获取 request, currUserId
        // 否则为 null
        val request = request
        val userId = currUserId
        asyncRecord(request, userId, setting)
    }

    /**
     * 执行查询操作
     */
    fun data(loggingType: LoggingType, param: SysLogParam): TablePage<SysLog> {
        param.loggingType = loggingType
        return sysLogRepository.logData(param)
    }

    /**
     * 根据 userId 查询日志
     */
    fun getTopLoginLog(userId: String): List<SysLog> {
        return sysLogRepository.selectTopLoginLog(userId)
    }

    /**
     * 清空日志记录
     */
    @Transactional(rollbackFor = [Exception::class])
    fun removeAll(): Boolean {
        return sysLogRepository.deleteAll()
    }

    @Async
    protected fun asyncRecord(
        request: HttpServletRequest, userId: String,
        setting: (LogRecord) -> Unit
    ) {
        val user = sysUserRepository.selectById(currUserId)
        val userAgent = request.userAgent
        val log = Entity.create<SysLog>().apply {
            operateAddress = request.remoteHost ?: "未知"
            method = request.method ?: "未知"
            createTime = LocalDateTime.now()
            operateUrl = request.requestURI ?: "未知"
            browser = userAgent.browser
            requestBody = request.queryString ?: ""
            systemOs = userAgent.system
            operateId = currUserId
            operateName = user?.username ?: "未登录用户"
        }
        setting(log)
        // 此处必须在多线程中调用 transaction, 否则事务不起作用
        add(log)
    }

    @Transactional(rollbackFor = [Exception::class])
    protected fun add(log: SysLog) {
        sysLogRepository.insert(log)
    }
}
