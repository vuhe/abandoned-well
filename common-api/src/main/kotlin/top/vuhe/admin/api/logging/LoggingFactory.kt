package top.vuhe.admin.api.logging

/**
 * ### 日志异步工厂
 *
 * @author vuhe
 */
interface LoggingFactory {
    /**
     * 执行日志入库操作
     */
    fun record(setting: (LogRecord) -> Unit)
}
