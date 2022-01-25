package top.vuhe.admin.schedule.service

import top.vuhe.admin.schedule.domain.ScheduleLog
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.web.request.PageDomain

/**
 * 定时任务日志服务接口
 *
 * @author vuhe
 */
interface IScheduleLogService {
    /**
     * 定时任务日志入库
     */
    fun add(entity: ScheduleLog): Boolean

    /**
     * 定时任务列表
     */
    fun list(param: ScheduleLog): List<ScheduleLog>

    /**
     * 定时任务分页
     */
    fun page(param: ScheduleLog, pageDomain: PageDomain): TablePage<ScheduleLog> {
        return TablePage(list(param), pageDomain)
    }
}
