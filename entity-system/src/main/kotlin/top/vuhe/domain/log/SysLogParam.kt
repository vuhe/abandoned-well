package top.vuhe.domain.log

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.greaterEq
import org.ktorm.dsl.lessEq
import org.ktorm.schema.ColumnDeclaring
import top.vuhe.logging.LoggingType
import top.vuhe.web.request.PageParam
import java.time.LocalDateTime

class SysLogParam : PageParam() {
    lateinit var loggingType: LoggingType
    var startTime: LocalDateTime? = null
    var endTime: LocalDateTime? = null

    override fun query(): ColumnDeclaring<Boolean> {
        return buildList {
            add(SysLogTable.loggingType eq loggingType)
            startTime?.let { start -> add(SysLogTable.createTime greaterEq start) }
            endTime?.let { end -> add(SysLogTable.createTime lessEq end) }
        }.reduce { a, b -> a and b }
    }
}
