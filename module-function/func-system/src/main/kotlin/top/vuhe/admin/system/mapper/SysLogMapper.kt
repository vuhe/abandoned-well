package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.enums.LoggingType
import top.vuhe.admin.api.enums.RequestMethod
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysLog
import java.time.LocalDateTime

/**
 * 日志接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class SysLogMapper : CurdMapper<SysLog>("sys_logging") {
    override val id = varchar("id").primaryKey().bind(SysLog::id)
    private val title = varchar("title").bind(SysLog::title)
    private val method = varchar("method").bind(SysLog::method)
    private val businessType = enum<BusinessType>("business_type").bind(SysLog::businessType)
    private val requestMethod = enum<RequestMethod>("request_method").bind(SysLog::requestMethod)
    private val operateId = varchar("operate_id").bind(SysLog::operateId)
    private val operateName = varchar("operate_name").bind(SysLog::operateName)
    private val operateUrl = varchar("operate_url").bind(SysLog::operateUrl)
    private val operateAddress = varchar("operate_address").bind(SysLog::operateAddress)
    private val requestParam = varchar("request_param").bind(SysLog::requestParam)
    private val responseBody = varchar("response_body").bind(SysLog::responseBody)
    private val success = boolean("success").bind(SysLog::success)
    private val errorMsg = text("error_msg").bind(SysLog::errorMsg)
    private val description = varchar("description").bind(SysLog::description)
    private val requestBody = varchar("request_body").bind(SysLog::requestBody)
    private val browser = varchar("browser").bind(SysLog::browser)
    private val systemOs = varchar("system_os").bind(SysLog::systemOs)
    private val loggingType = enum<LoggingType>("logging_type").bind(SysLog::loggingType)

    private val createTime = datetime("create_time").bind(SysLog::createTime)

    /**
     * 条件查询日志
     */
    fun logData(loggingType: LoggingType, startTime: LocalDateTime?, endTime: LocalDateTime?): List<SysLog> {
        return database.from(this).select().whereWithConditions {
            it.add(this.loggingType eq loggingType)
            if (startTime != null) it.add(createTime greaterEq startTime)
            if (endTime != null) it.add(createTime lessEq endTime)
        }.orderBy(createTime.desc()).map { createEntity(it) }
    }

    /**
     * 查询前 10 条登录记录
     */
    fun selectTopLoginLog(userId: String): List<SysLog> {
        return database.from(this).select().whereWithConditions {
            it.add(loggingType eq LoggingType.LOGIN)
            it.add(operateId eq userId)
        }.orderBy(createTime.desc()).limit(10)
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
