package top.vuhe.domain.log

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.database.table.TablePage
import top.vuhe.logging.LogRecord
import top.vuhe.logging.LoggingFactory
import top.vuhe.logging.LoggingType
import top.vuhe.network.requestContext
import top.vuhe.security.securityContext

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
    private val currUserId by securityContext()

    override fun record(setting: (LogRecord) -> Unit) {
        // 在进入其他线程前获取 request, currUserId 否则为 null
        val userId = kotlin.runCatching { currUserId }.getOrElse { "" }
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
