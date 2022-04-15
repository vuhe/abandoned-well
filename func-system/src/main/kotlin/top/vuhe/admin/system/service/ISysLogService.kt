package top.vuhe.admin.system.service

import top.vuhe.admin.api.logging.LoggingFactory
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.param.SysLogParam

/**
 * 日志服务接口
 *
 * @author vuhe
 */
interface ISysLogService : LoggingFactory {
    /**
     * 执行查询操作
     */
    fun data(loggingType: LoggingType, param: SysLogParam): TablePage<SysLog>

    /**
     * 根据 id 查询日志
     */
    fun getById(id: String): SysLog?

    /**
     * 根据 userId 查询日志
     */
    fun getTopLoginLog(userId: String): List<SysLog>

    /**
     * 清空日志记录
     */
    fun removeAll(): Boolean
}
