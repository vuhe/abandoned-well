package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.system.param.SysLogParam
import top.vuhe.admin.system.service.ISysLogService

/**
 * 日志控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "系统日志")
@RequestMapping(API_SYSTEM_PREFIX + "log")
class SysLogController(
    private val sysLogService: ISysLogService
) : BaseController() {

    /**
     * 行为日志视图
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:log:main')")
    fun main() = ModelAndView("system/log/main")

    /**
     * 日志详情
     */
    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:log:info')")
    fun details() = ModelAndView("system/log/info")

    /* -------------------------------------------------------------------------- */

    /**
     * 操作日志数据
     */
    @GetMapping("operateLog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:log:operateLog')")
    fun operateLog(param: SysLogParam) = pageTable {
        sysLogService.data(LoggingType.OPERATE, param)
    }

    /**
     * 登录日志数据
     */
    @GetMapping("loginLog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:log:loginLog')")
    fun loginLog(param: SysLogParam) = pageTable {
        sysLogService.data(LoggingType.LOGIN, param)
    }

    /**
     * 清空日志
     */
    @DeleteMapping("/removeAll")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:log:remove')")
    fun removeAll() = boolResult { sysLogService.removeAll() }
}
