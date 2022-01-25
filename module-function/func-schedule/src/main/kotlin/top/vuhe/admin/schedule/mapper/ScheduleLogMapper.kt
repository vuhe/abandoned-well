package top.vuhe.admin.schedule.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.schedule.domain.ScheduleLog
import top.vuhe.admin.spring.database.mapper.CurdMapper

/**
 * 定时任务日志接口
 *
 * @author vuhe
 */
@Repository
class ScheduleLogMapper : CurdMapper<ScheduleLog>("schedule_log") {
    override val id = varchar("log_id").primaryKey().bind(ScheduleLog::logId)
    private val jobId = varchar("job_id").bind(ScheduleLog::jobId)
    private val beanName = varchar("bean_name").bind(ScheduleLog::beanName)
    private val params = varchar("params").bind(ScheduleLog::params)
    private val status = int("status").bind(ScheduleLog::status)
    private val error = varchar("error").bind(ScheduleLog::error)
    private val times = int("times").bind(ScheduleLog::times)
    private val createTime = datetime("create_time").bind(ScheduleLog::createTime)

    private object JobLogMap: Table<Nothing>("schedule_job") {
        val id = varchar("job_id").primaryKey()
        val jobName = varchar("job_name")
    }

    /**
     * 通过 job name 查询 log
     */
    fun selectListByJobName(param: ScheduleLog): List<ScheduleLog> {
        return database.from(this)
            .leftJoin(JobLogMap, on = jobId eq JobLogMap.id)
            .select(columns)
            .whereWithConditions {
                if (param.jobName.isNotEmpty()) {
                    it.add(JobLogMap.jobName like "%${param.jobName}%")
                }
            }
            .orderBy(createTime.desc())
            .map { createEntity(it) }
    }
}
