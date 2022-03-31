package top.vuhe.admin.api.logging

import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.enums.LoggingType

/**
 * ### 记录日志信息
 *
 * @author vuhe
 */
interface LogRecord {
    /**
     * 标题
     */
    var title: String

    /**
     * 描述
     */
    var description: String

    /**
     * 业务类型
     */
    var businessType: BusinessType

    /**
     * 接 口 执 行 状 态
     */
    var success: Boolean

    /**
     * 日 志 类 型
     */
    var loggingType: LoggingType

    /**
     * 异 常 信 息
     */
    var errorMsg: String
}
