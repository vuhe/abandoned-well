package top.vuhe.admin.schedule.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SCHEDULE_PREFIX
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.schedule.service.IScheduleJobService
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj

/**
 * 定时任务控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "定时任务")
@RequestMapping(API_SCHEDULE_PREFIX + "job")
class ScheduleJobController(
    private val scheduleJobService: IScheduleJobService
) : BaseController() {

    /**
     * 获取定时任务列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/schdule/job/main','sch:job:main')")
    fun main() = ModelAndView("schedule/job/main")

    /**
     * 获取定时任务新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/schdule/job/add','sch:job:add')")
    fun add() = ModelAndView("schedule/job/add")


    /**
     * 获取定时任务修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/schdule/job/edit','sch:job:edit')")
    fun edit(jobId: String) = ModelAndView("schedule/job/edit").apply {
        addObject("scheduleJob", scheduleJobService.getOneById(jobId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 获取定时任务列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/schdule/job/data','sch:job:data')")
    fun data(pageDomain: PageDomain, param: ScheduleJob) = pageTable {
        scheduleJobService.page(param, pageDomain)
    }

    /**
     * 保存定时任务数据
     */
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/schdule/job/add','sch:job:add')")
    fun save(@RequestBody scheduleJob: ScheduleJob) = boolResult {
        scheduleJobService.add(scheduleJob)
    }

    /**
     * 执行一次定时任务
     */
    @PutMapping("/run")
    @PreAuthorize("hasPermission('/schdule/job/run','sch:job:run')")
    fun run(jobId: String): ResultObj<*> {
        scheduleJobService.run(jobId)
        return ResultObj.Success<Nothing>(message = "运行成功")
    }

    /**
     * 更新定时任务数据
     */
    @PutMapping("/update")
    @PreAuthorize("hasPermission('/schdule/job/edit','sch:job:edit')")
    fun update(@RequestBody scheduleJob: ScheduleJob) = boolResult {
        scheduleJobService.modify(scheduleJob)
    }

    /**
     * 停止定时任务
     */
    @PutMapping("/pause")
    @PreAuthorize("hasPermission('/schdule/job/pause','sch:job:pause')")
    fun pauseJob(jobId: String) = boolResult("停止成功", "停止失败") {
        scheduleJobService.pause(jobId)
    }

    /**
     * 恢复定时任务
     */
    @PutMapping("/resume")
    @PreAuthorize("hasPermission('/schdule/job/resume','sch:job:resume')")
    fun resumeJob(jobId: String) = boolResult("恢复成功", "恢复失败") {
        scheduleJobService.resume(jobId)
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/schdule/job/remove','sch:job:remove')")
    fun deleteJob(@PathVariable("id") jobId: String) = boolResult("删除成功", "删除失败") {
        scheduleJobService.resume(jobId)
    }
}
