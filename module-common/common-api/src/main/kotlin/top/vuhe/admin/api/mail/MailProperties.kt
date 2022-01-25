package top.vuhe.admin.api.mail

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 邮件配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("project.mail")
object MailProperties {
    /**
     * 是否启用邮件功能
     */
    var enable: Boolean = false

    /**
     * 邮件发送者服务器地址
     */
    var host: String = ""

    /**
     * 邮件发送者服务器端口
     */
    var port: Int = 0

    /**
     * 邮件发送者邮箱
     */
    var from: String = ""

    /**
     * 邮件发送者用户名称
     */
    var user: String = ""

    /**
     * 邮件发送者邮箱密码
     */
    var password: String = ""
}
