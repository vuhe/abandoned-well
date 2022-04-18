package top.vuhe.admin.system.repository

import org.ktorm.dsl.and
import org.ktorm.dsl.desc
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.sortedBy
import org.ktorm.entity.take
import org.ktorm.entity.toList
import org.springframework.stereotype.Repository
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.system.domain.SysLog
import top.vuhe.admin.system.param.SysLogParam
import top.vuhe.admin.system.table.SysLogTable

/**
 * 日志接口
 *
 * @author vuhe
 */
@Repository
class SysLogRepository : CurdRepository<SysLogTable, SysLog>() {

    override val table get() = SysLogTable

    override var SysLog.entityId: String by SysLog::id

    /**
     * 条件查询日志
     */
    fun logData(param: SysLogParam): TablePage<SysLog> {
        return entities.filter { param.query() }
            .sortedBy { it.createTime.desc() }
            .toPage(param)
    }

    /**
     * 查询前 10 条登录记录
     */
    fun selectTopLoginLog(userId: String): List<SysLog> {
        return entities.filter {
            (it.loggingType eq LoggingType.LOGIN) and (it.operateId eq userId)
        }.sortedBy { it.createTime.desc() }.take(10).toList()
    }

    /**
     * 清空日志
     */
    fun deleteAll(): Boolean {
        database.useConnection { conn ->
            val sql = "truncate table ${table.tableName}"
            conn.prepareStatement(sql).use { it.execute() }
        }
        return true
    }
}
