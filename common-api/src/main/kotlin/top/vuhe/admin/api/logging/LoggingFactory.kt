package top.vuhe.admin.api.logging

import org.springframework.scheduling.annotation.Async

/**
 * ### 日志异步工厂
 *
 * @author vuhe
 */
interface LoggingFactory {
    /**
     * 执行日志入库操作
     */
    @Async
    fun record(setting: (LogRecord) -> Unit)
}
