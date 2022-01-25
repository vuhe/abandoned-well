package top.vuhe.admin.schedule.handler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.schedule.service.IScheduleLogService
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PreDestroy

/**
 * 定时任务调度中心
 *
 * @author vuhe
 */
@Component
class ScheduleCenter(private val logService: IScheduleLogService) {
    /**
     * 用于记录系统中已经生成的任务,
     * jobId -> taskRunner
     */
    private val jobMap = ConcurrentHashMap<String, TaskRunner>()

    /**
     * 任务管理池
     */
    private val threadPoolTaskScheduler = ThreadPoolTaskScheduler()

    /**
     * 开始任务(更新)
     */
    fun startJob(job: ScheduleJob) {
        val task = TaskRunner(job, logService::add)
        // 将 job 信息放入 map
        jobMap[job.jobId] = task

        // 如果状态为暂停，则不开启任务
        if (job.status == "1") return

        // 加入计划
        val future = try {
            threadPoolTaskScheduler.schedule(task.runnable, CronTrigger(job.cronExpression))
        } catch (e: Exception) {
            log.error("加入任务失败！", e)
            // 由于任务启动失败，设置状态为「暂停」
            job.status = "1"
            null
        }

        // 更新 future 状态
        task.future = future
    }

    /**
     * 暂停任务(取消任务)
     */
    fun pauseJob(jobId: String) {
        jobMap[jobId]?.cancel()
    }

    /**
     * 删除任务
     */
    fun deleteJob(jobId: String) {
        pauseJob(jobId)
        jobMap.remove(jobId)
    }

    /**
     * 结束所有任务
     */
    @PreDestroy
    fun cancelAll() {
        jobMap.forEach { (_, task) -> task.cancel() }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
