package top.vuhe.admin.system.service

import org.ktorm.entity.Entity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.api.network.userAgent
import top.vuhe.admin.spring.security.principal.LoginUserInfo
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.repository.SysLogRepository
import top.vuhe.admin.system.repository.SysUserRepository
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
        val user = sysUserRepository.selectById(LoginUserInfo.currUserId)
        val userAgent = request.userAgent
        val log = Entity.create<SysLog>().apply {
            operateAddress = request.remoteHost ?: "未知"
            method = request.method ?: "未知"
            createTime = LocalDateTime.now()
            operateUrl = request.requestURI ?: "未知"
            browser = userAgent.browser
            requestBody = request.queryString ?: ""
            systemOs = userAgent.system
            operateId = LoginUserInfo.currUserId
            operateName = user?.username ?: "未知"
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
