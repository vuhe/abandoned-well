package top.vuhe.admin.schedule.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 定时任务日志记录
 *
 * @author vuhe
 */
class ScheduleLog : BaseEntity() {
    override val id: String get() = logId

    /**
     * 日志编号
     */
    var logId: String = ""

    /**
     * 任务编号
     */
    var jobId: String = ""

    /**
     * 任务名称
     */
    var jobName: String = ""

    /**
     * 运行类
     */
    var beanName: String = ""

    /**
     * 参数集合
     */
    var params: String = ""

    /**
     * 日志状态
     */
    var status: Int? = null

    /**
     * 异常信息
     */
    var error: String = ""

    /**
     * 运行时长
     */
    var times: Int? = null

    /**
     * 用于记录日志的中间字段，
     * 不会写入数据库
     */
    var beginTime: Long = 0
}
