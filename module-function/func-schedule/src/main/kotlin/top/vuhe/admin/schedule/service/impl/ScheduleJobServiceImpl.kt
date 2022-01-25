package top.vuhe.admin.schedule.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.schedule.handler.ScheduleCenter
import top.vuhe.admin.schedule.mapper.ScheduleJobMapper
import top.vuhe.admin.schedule.service.IScheduleJobService
import top.vuhe.admin.spring.database.service.impl.CurdService

/**
 * ### 定时任务服务
 * 由于组装 bean 需要，此 bean 由 configuration 管理
 *
 * @author vuhe
 */
@Service
class ScheduleJobServiceImpl(
    private val jobCenter: ScheduleCenter,
    private val jobMapper: ScheduleJobMapper
) : CurdService<ScheduleJob>(jobMapper), IScheduleJobService {

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: ScheduleJob): Boolean {
        jobCenter.startJob(entity)
        return super.add(entity)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modify(entity: ScheduleJob): Boolean {
        if (jobMapper.selectById(entity.jobId) == null) return false
        jobCenter.startJob(entity)
        return super.modify(entity)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun pause(jobId: String): Boolean {
        val job = jobMapper.selectById(jobId) ?: return false
        job.status = "1"
        jobCenter.pauseJob(jobId)
        return jobMapper.update(job) > 0
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun resume(jobId: String): Boolean {
        val job = jobMapper.selectById(jobId) ?: return false
        job.status = "0"
        jobCenter.startJob(job)
        return jobMapper.update(job) > 0
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun run(jobId: String) {
        val job = jobMapper.selectById(jobId) ?: return
        jobCenter.startJob(job)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        ids.forEach { jobCenter.deleteJob(it) }
        return super.batchRemove(ids)
    }
}
