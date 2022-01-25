package top.vuhe.admin.schedule.service

import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.spring.database.service.ICurdService

/**
 * 定时任务服务接口
 *
 * @author vuhe
 */
interface IScheduleJobService : ICurdService<ScheduleJob> {
    /**
     * 停止定时任务
     */
    fun pause(jobId: String): Boolean

    /**
     * 恢复定时任务
     */
    fun resume(jobId: String): Boolean

    /**
     * 运行一次定时任务
     */
    fun run(jobId: String)
}
