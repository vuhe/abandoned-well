package top.vuhe.admin.well.mapper

import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.well.domina.WellLog

/**
 * 井修改日志接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class LogMapper : CurdMapper<WellLog>("well_log") {
    override val id = varchar("id").primaryKey().bind(WellLog::id)
    private val wellId = varchar("well_id").bind(WellLog::wellId)
    private val logType = varchar("log_type").bind(WellLog::logType)
    private val username = varchar("username").bind(WellLog::username)
}
