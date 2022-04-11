package top.vuhe.admin.system.domain

import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.api.logging.LoggingType
import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 日志实体类
 *
 * @author vuhe
 */
class SysLog : BaseEntity(), LogRecord {
    var id by varchar("id").primary()
    override var title by varchar("title")
    override var description by varchar("description")
    override var businessType by enum("business_type", BusinessType.OTHER)
    override var success by boolean("success")
    override var loggingType by enum("logging_type", LoggingType.OPERATE)
    override var errorMsg by varchar("error_msg")

    /**
     * 请求的方法
     */
    var method by varchar("method")

    /**
     * 请求的连接
     */
    var operateUrl by varchar("operate_url")

    /**
     * 用户 IP 地址
     */
    var operateAddress by varchar("operate_address")

    /**
     * 请 求 参 数
     */
    var requestParam by varchar("request_param")

    /**
     * 获 取 请 求 体
     */
    var requestBody by varchar("request_body")

    /**
     * 接 口 响 应 数 据
     */
    var responseBody by varchar("response_body")

    /**
     * 使用浏览器
     */
    var browser by varchar("browser")

    /**
     * 操作系统
     */
    var systemOs by varchar("system_os")

    /**
     * 操 作 人 员 id
     */
    var operateId by varchar("operate_id")

    /**
     * 操 作 人 员
     */
    var operateName by varchar("operate_name")

    /** 创建时间 */
    var createTime by datetime("create_time")
}
