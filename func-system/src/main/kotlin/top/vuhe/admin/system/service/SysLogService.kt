package top.vuhe.admin.system.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.api.network.requestContext
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.param.SysLogParam
import top.vuhe.admin.system.repository.SysLogRepository

/**
 * 日志服务接口实现
 *
 * @author vuhe
 */
@Service
class SysLogService(
    private val loggingService: LoggingService,
    private val sysLogRepository: SysLogRepository
) : LoggingFactory {
    private val request by requestContext()

    override fun record(setting: (LogRecord) -> Unit) {
        // 在进入其他线程前获取 request, currUserId
        // 否则为 null
        val request = request
        val userId = currUserId
        loggingService.asyncRecord(request, userId, setting)
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
}
