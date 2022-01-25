package top.vuhe.admin.schedule.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.spring.database.mapper.CurdMapper

/**
 * 定时任务接口
 *
 * @author vuhe
 */
@Repository
class ScheduleJobMapper : CurdMapper<ScheduleJob>("schedule_job") {
    override val id = varchar("job_id").primaryKey().bind(ScheduleJob::jobId)
    private val jobName = varchar("job_name").bind(ScheduleJob::jobName)
    private val beanName = varchar("bean_name").bind(ScheduleJob::beanName)
    private val params = varchar("params").bind(ScheduleJob::params)
    private val cronExpression = varchar("cron_expression").bind(ScheduleJob::cronExpression)
    private val status = varchar("status").bind(ScheduleJob::status)
    private val createTime = datetime("create_time").bind(ScheduleJob::createTime)
    private val remark = varchar("remark").bind(ScheduleJob::remark)

    override fun Query.listFilter(param: ScheduleJob): Query {
        return whereWithConditions {
            if (param.jobName.isNotEmpty()) it.add(jobName like "%${param.jobName}%")
        }
    }
}
