package top.vuhe.admin.schedule

/**
 * 定时任务服务接口
 *
 * @author vuhe
 */
interface ScheduleTask : (String) -> Unit {
    /**
     * 任 务 实 现
     */
    override fun invoke(params: String)
}
