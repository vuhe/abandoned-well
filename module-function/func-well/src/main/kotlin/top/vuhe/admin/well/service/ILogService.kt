package top.vuhe.admin.well.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.well.domina.WellLog

/**
 * 井修改日志服务类
 *
 * @author vuhe
 */
interface ILogService : ICurdService<WellLog> {
    /**
     * 记录日志信息
     */
    fun record(wellId: String, logType: String)
}
