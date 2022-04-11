package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.api.network.browser
import top.vuhe.admin.api.network.queryParam
import top.vuhe.admin.api.network.requestContext
import top.vuhe.admin.api.network.systemType
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.mapper.SysLogMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import top.vuhe.admin.system.service.ISysLogService
import java.time.LocalDateTime

/**
 * 日志服务接口实现
 *
 * @author vuhe
 */
@Service
class SysLogServiceImpl : ISysLogService {
    private val sysUserMapper = SysUserMapper
    private val sysLogMapper = SysLogMapper
    private val request by requestContext()

    @Transactional(rollbackFor = [Exception::class])
    override fun record(setting: (LogRecord) -> Unit) {
        val user = sysUserMapper.selectById(currUserId)
        val request = requireNotNull(request) { "内部错误(request is null)" }
        val log = SysLog().apply {
            operateAddress = request.remoteHost ?: "未知"
            method = request.method ?: "未知"
            createTime = LocalDateTime.now()
            operateUrl = request.requestURI ?: "未知"
            browser = request.browser
            requestBody = request.queryParam
            systemOs = request.systemType
            operateId = currUserId
            operateName = user?.username ?: "未登录用户"
        }
        setting(log)
        sysLogMapper.insert(log)
    }

    override fun data(loggingType: LoggingType, startTime: LocalDateTime?, endTime: LocalDateTime?): List<SysLog> {
        return sysLogMapper.logData(loggingType, startTime, endTime)
    }

    override fun getById(id: String): SysLog? {
        return sysLogMapper.selectById(id)
    }

    override fun getTopLoginLog(userId: String): List<SysLog> {
        return sysLogMapper.selectTopLoginLog(userId)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun removeAll(): Boolean {
        return sysLogMapper.deleteAll()
    }
}
