package top.vuhe.admin.schedule.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SCHEDULE_PREFIX
import top.vuhe.admin.schedule.domain.ScheduleLog
import top.vuhe.admin.schedule.service.IScheduleLogService
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain

/**
 * 定时任务日志控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "任务日志")
@RequestMapping(API_SCHEDULE_PREFIX + "log")
class ScheduleLogController(
    private val scheduleLogService: IScheduleLogService
) : BaseController() {

    /**
     * 获取定时任务日志列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/schdule/log/main','sch:log:main')")
    fun main() = ModelAndView("schedule/log/main")

    /**
     * 获取定时任务日志列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/schdule/log/data','sch:log:data')")
    fun data(scheduleLogBean: ScheduleLog, pageDomain: PageDomain) = pageTable {
        scheduleLogService.page(scheduleLogBean, pageDomain)
    }
}
