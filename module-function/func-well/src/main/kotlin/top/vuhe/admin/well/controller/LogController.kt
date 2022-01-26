package top.vuhe.admin.well.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.ILogService

/**
 * 井信息日志管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/log")
class LogController(
    private val logService: ILogService
) : BaseController() {
}
