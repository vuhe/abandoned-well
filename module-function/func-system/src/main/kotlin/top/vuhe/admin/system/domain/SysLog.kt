package top.vuhe.admin.system.domain

import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.enums.LoggingType
import top.vuhe.admin.api.logging.LogRecord
import top.vuhe.admin.spring.database.entity.BaseEntity
import java.time.LocalDateTime

/**
 * 日志实体类
 *
 * @author vuhe
 */
class SysLog : BaseEntity(), LogRecord {
    override var id: String = ""
    override var title: String = ""
    override var description: String = ""
    override var businessType: BusinessType? = BusinessType.OTHER
    override var success: Boolean? = true
    override var loggingType: LoggingType? = LoggingType.OPERATE
    override var errorMsg: String = ""

    /**
     * 请求的方法
     */
    var method: String = ""

    /**
     * 请求的连接
     */
    var operateUrl: String = ""

    /**
     * 用户 IP 地址
     */
    var operateAddress: String = ""

    /**
     * 请 求 参 数
     */
    var requestParam: String = ""

    /**
     * 获 取 请 求 体
     */
    var requestBody: String = ""

    /**
     * 接 口 响 应 数 据
     */
    var responseBody: String = ""

    /**
     * 使用浏览器
     */
    var browser: String = ""

    /**
     * 操作系统
     */
    var systemOs: String = ""

    /**
     * 操 作 人 员 id
     */
    var operateId: String = ""

    /**
     * 操 作 人 员
     */
    var operateName: String = ""

    /** 创建时间 */
    var createTime: LocalDateTime? = null
}
