package top.vuhe.admin.schedule.handler

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.schedule.service.IScheduleJobService

/**
 * 定时任务启动处理类
 *
 * @author vuhe
 */
class ScheduleStarted : ApplicationListener<ApplicationStartedEvent> {
    private val emptyParam = ScheduleJob()

    /**
     * 在 bean 注册完成后，将数据库中的 job 初始化
     */
    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val context = event.applicationContext
        val jobCenter = context.getBean(ScheduleCenter::class.java)
        val jobService = context.getBean(IScheduleJobService::class.java)
        val jobList = jobService.list(emptyParam)
        jobList.forEach { jobCenter.startJob(it) }
    }
}
