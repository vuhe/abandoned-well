package top.vuhe.admin.api.mail

import cn.hutool.extra.mail.MailAccount
import cn.hutool.extra.mail.MailUtil

/**
 * ### 邮件发送服务
 *
 * @author vuhe
 */
object MailService {
    private val mailProperties = MailProperties

    private val mailAccount = MailAccount().apply {
        host = mailProperties.host
        port = mailProperties.port
        from = mailProperties.from
        user = mailProperties.user
        pass = mailProperties.password
        isAuth = true
    }

    /**
     * 是否启用邮件服务
     */
    val enable: Boolean get() = mailProperties.enable

    /**
     * ### 发送邮件
     * 如果邮件配置不开启，则邮件发送始终为错误
     *
     * @param tos     收件人列表
     * @param subject 标题
     * @param content 正文
     * @param isHtml  是否为HTML格式
     */
    fun send(tos: List<String>, subject: String, content: String, isHtml: Boolean = false): Boolean {
        return if (mailProperties.enable) {
            MailUtil.send(
                mailAccount, tos,
                subject.ifEmpty { null }, content, isHtml
            ).isNotEmpty()
        } else false
    }
}
