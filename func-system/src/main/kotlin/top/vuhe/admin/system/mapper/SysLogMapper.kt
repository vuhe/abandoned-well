package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.Column
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysLog
import java.time.LocalDateTime

/**
 * 日志接口
 *
 * @author vuhe
 */
object SysLogMapper : CurdMapper<SysLog>("sys_logging") {
    /**
     * 条件查询日志
     */
    fun logData(loggingType: LoggingType, startTime: LocalDateTime?, endTime: LocalDateTime?): List<SysLog> {
        return selectByConditions(col("create_time").desc()) {
            it.add(col("logging_type") eq loggingType)

            @Suppress("UNCHECKED_CAST")
            val createTime = col("create_time") as Column<LocalDateTime>
            if (startTime != null) it.add(createTime greaterEq startTime)
            if (endTime != null) it.add(createTime lessEq endTime)
        }
    }

    /**
     * 查询前 10 条登录记录
     */
    fun selectTopLoginLog(userId: String): List<SysLog> {
        return database.from(this).select().whereWithConditions {
            it.add(col("logging_type") eq LoggingType.LOGIN)
            it.add(col("operate_id") eq userId)
        }.orderBy(col("create_time").desc()).limit(10)
            .map { createEntity(it) }
    }

    /**
     * 清空日志
     */
    fun deleteAll(): Boolean {
        database.useConnection { conn ->
            val sql = "truncate table $tableName"
            conn.prepareStatement(sql).use { it.execute() }
        }
        return true
    }
}
