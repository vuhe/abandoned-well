package top.vuhe.admin.system.mapper

import org.ktorm.dsl.Query
import org.ktorm.dsl.like
import org.ktorm.dsl.whereWithConditions
import org.ktorm.schema.datetime
import org.ktorm.schema.text
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysNotice

/**
 * 系统通知接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class SysNoticeMapper : CurdMapper<SysNotice>("sys_notice") {
    override val id = varchar("id").primaryKey().bind(SysNotice::id)
    private val title = varchar("title").bind(SysNotice::title)
    private val content = text("content").bind(SysNotice::content)
    private val sender = varchar("sender").bind(SysNotice::sender)
    private val accept = varchar("accept").bind(SysNotice::accept)
    private val type = varchar("type").bind(SysNotice::type)

    private val createTime = datetime("create_time").bind(SysNotice::createTime)
    private val createBy = varchar("create_by").bind(SysNotice::createBy)
    private val updateTime = datetime("update_time").bind(SysNotice::updateTime)
    private val updateBy = varchar("update_by").bind(SysNotice::updateBy)
    private val remark = varchar("remark").bind(SysNotice::remark)

    @Suppress("DuplicatedCode")
    override fun Query.listFilter(param: SysNotice): Query {
        return whereWithConditions {
            if (param.title.isNotEmpty()) it.add(title like "%${param.title}%")
            if (param.type.isNotEmpty()) it.add(type like "%${param.type}%")
            if (param.accept.isNotEmpty()) it.add(accept like "%${param.accept}%")
        }
    }
}
