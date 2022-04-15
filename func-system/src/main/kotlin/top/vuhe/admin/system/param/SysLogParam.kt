package top.vuhe.admin.system.param

import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.web.request.PageParam
import java.time.LocalDateTime

class SysLogParam : PageParam() {
    lateinit var loggingType: LoggingType
    var startTime: LocalDateTime? = null
    var endTime: LocalDateTime? = null
}
