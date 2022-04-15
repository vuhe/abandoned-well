package top.vuhe.admin.system.table

import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.enum
import org.ktorm.schema.varchar
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.database.table.IdTable
import top.vuhe.admin.system.domain.SysLog

object SysLogTable : IdTable<SysLog>("sys_logging") {
    override val id = varchar("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val description = varchar("description").bindTo { it.description }
    val businessType = enum<BusinessType>("business_type").bindTo { it.businessType }
    val success = boolean("success").bindTo { it.success }
    val loggingType = enum<LoggingType>("logging_type").bindTo { it.loggingType }
    val errorMsg = varchar("error_msg").bindTo { it.errorMsg }
    val method = varchar("method").bindTo { it.method }
    val operateUrl = varchar("operate_url").bindTo { it.operateUrl }
    val operateAddress = varchar("operate_address").bindTo { it.operateAddress }
    val requestParam = varchar("request_param").bindTo { it.requestParam }
    val requestBody = varchar("request_body").bindTo { it.requestBody }
    val responseBody = varchar("response_body").bindTo { it.responseBody }
    val browser = varchar("browser").bindTo { it.browser }
    val systemOs = varchar("system_os").bindTo { it.systemOs }
    val operateId = varchar("operate_id").bindTo { it.operateId }
    val operateName = varchar("operate_name").bindTo { it.operateName }
    val createTime = datetime("create_time").bindTo { it.createTime }
}
