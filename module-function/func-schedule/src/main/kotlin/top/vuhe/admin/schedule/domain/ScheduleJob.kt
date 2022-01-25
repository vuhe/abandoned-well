package top.vuhe.admin.schedule.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 定时任务配置管理
 *
 * @author vuhe
 */
class ScheduleJob : BaseEntity() {
    override val id: String get() = jobId

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
     * 携带参数
     */
    var params: String = ""

    /**
     * cron 表达式
     */
    var cronExpression: String = ""

    /**
     * 状态
     */
    var status: String = ""

    /**
     * 分组编号
     */
    var groupId: String = ""
}
