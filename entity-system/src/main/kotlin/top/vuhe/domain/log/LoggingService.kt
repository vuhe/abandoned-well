package top.vuhe.domain.log

import org.ktorm.entity.Entity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.domain.user.SysUserRepository
import top.vuhe.logging.LogRecord
import top.vuhe.network.userAgent
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Service
class LoggingService(
    private val sysUserRepository: SysUserRepository,
    private val sysLogRepository: SysLogRepository
) {
    @Async("defaultExecutor")
    fun asyncRecord(
        request: HttpServletRequest, userId: String,
        setting: (LogRecord) -> Unit
    ) {
        val user = sysUserRepository.selectById(userId)
        val userAgent = request.userAgent
        val log = Entity.create<SysLog>().apply {
            operateAddress = request.remoteHost ?: "Unknown"
            method = request.method ?: "Unknown"
            createTime = LocalDateTime.now()
            operateUrl = request.requestURI ?: "Unknown"
            browser = userAgent.browser
            requestParam = request.queryString ?: ""
            systemOs = userAgent.system
            operateId = userId
            operateName = user?.username ?: "Unknown"
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
