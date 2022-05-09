package top.vuhe.admin.api.logging

/**
 * ## 记录日志信息
 *
 * @author vuhe
 */
interface LogRecord {
    /** 标题 */
    var title: String

    /** 描述 */
    var description: String

    /** 业务类型 */
    var businessType: BusinessType

    /** 接口执行状态 */
    var success: Boolean

    /** 日志类型 */
    var loggingType: LoggingType

    /** 异常信息 */
    var errorMsg: String
}
