package top.vuhe.admin.schedule.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.schedule.domain.ScheduleLog
import top.vuhe.admin.schedule.mapper.ScheduleLogMapper
import top.vuhe.admin.schedule.service.IScheduleLogService

/**
 * 定时任务日志服务
 *
 * @author vuhe
 */
@Service("scheduleLogService")
class ScheduleLogServiceImpl(
    private val jobLogMapper: ScheduleLogMapper
) : IScheduleLogService {

    override fun add(entity: ScheduleLog): Boolean {
        return jobLogMapper.insert(entity) > 0
    }

    override fun list(param: ScheduleLog): List<ScheduleLog> {
        return jobLogMapper.selectListByJobName(param)
    }
}
