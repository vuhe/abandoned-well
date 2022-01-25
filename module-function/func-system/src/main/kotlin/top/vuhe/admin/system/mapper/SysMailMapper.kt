package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysMail

/**
 * 邮件服务持久层接口
 *
 * @author vuhe
 */
@Repository
class SysMailMapper : CurdMapper<SysMail>("sys_mail") {
    override val id = varchar("mail_id").primaryKey().bind(SysMail::mailId)
    private val receiver = varchar("receiver").bind(SysMail::receiver)
    private val subject = varchar("subject").bind(SysMail::subject)
    private val content = text("content").bind(SysMail::content)

    private val createTime = datetime("create_time").bind(SysMail::createTime)
    private val createBy = varchar("create_by").bind(SysMail::createBy)

    override fun Query.listFilter(param: SysMail): Query {
        return whereWithConditions {
            if (param.receiver.isNotEmpty()) it.add(receiver like "%${param.receiver}%")
            if (param.subject.isNotEmpty()) it.add(subject like "%${param.subject}%")
            if (param.content.isNotEmpty()) it.add(content like "%${param.content}%")
            if (param.createBy.isNotEmpty()) it.add(createBy like "%${param.createBy}%")
        }
    }
}
