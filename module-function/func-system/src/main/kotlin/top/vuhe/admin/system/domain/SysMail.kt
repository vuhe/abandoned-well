package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * ### 邮件发送实体
 * 发送人: createBy
 *
 * @author vuhe
 */
class SysMail : BaseEntity() {
    override val id: String get() = mailId

    /**
     * 邮件id
     */
    var mailId: String = ""

    /**
     * 接收人（邮箱）
     */
    var receiver: String = ""

    /**
     * 邮件主体
     */
    var subject: String = ""

    /**
     * 邮件正文
     */
    var content: String = ""
}
