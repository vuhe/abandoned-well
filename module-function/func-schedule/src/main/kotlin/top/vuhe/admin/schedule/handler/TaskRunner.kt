package top.vuhe.admin.schedule.handler

import org.slf4j.LoggerFactory
import top.vuhe.admin.spring.database.entity.column.IdMaker
import top.vuhe.admin.schedule.ScheduleTask
import top.vuhe.admin.schedule.domain.ScheduleJob
import top.vuhe.admin.schedule.domain.ScheduleLog
import java.time.LocalDateTime
import java.util.concurrent.ScheduledFuture

/**
 * ### 任务构建器
 * 将数据库中的任务转换为可运行的任务
 *
 * @author vuhe
 */
class TaskRunner(
    private val scheduleJob: ScheduleJob,
    private val finallyHandle: (ScheduleLog) -> Unit
) {
    var future: ScheduledFuture<*>? = null

    /**
     * 生成执行对象
     */
    val runnable = object : Runnable {
        private val job = scheduleJob
        override fun run() {
            val log = createLog(job)
            try {
                val task = getTask(job.beanName)
                // 执行
                task(job.params)
                successLog(log)
            } catch (e: Exception) {
                failLog(log, e)
            } finally {
                finallyHandle(log)
            }
        }
    }

    /**
     * 取消此任务
     */
    fun cancel() {
        future?.cancel(true)
        future = null
    }

    /**
     * 获取任务执行器
     */
    private fun getTask(className: String): ScheduleTask {
        return try {
            val clazz = Class.forName(className)
            val obj = clazz.getDeclaredConstructor().newInstance()
            ScheduleTask::class.java.cast(obj) as ScheduleTask
        } catch (e: Exception) {
            logger.error("Task class can't find, task cancel ! ")
            throw e
        }
    }

    /**
     * 创建任务日志
     */
    private fun createLog(jobBean: ScheduleJob) = ScheduleLog().apply {
        logId = IdMaker.next().toString()
        jobId = jobBean.jobId
        beanName = jobBean.beanName
        params = jobBean.params
        createTime = LocalDateTime.now()
        // 默认值
        status = 1
        error = ""
        beginTime = System.currentTimeMillis()
    }

    /**
     * 记录成功日志
     */
    private fun successLog(log: ScheduleLog) {
        val executeTime = System.currentTimeMillis() - log.beginTime
        log.times = executeTime.toInt()
        log.status = 0
        // 控制台日志记录
        logger.info("定时器 === >> " + log.jobName + "执行成功,耗时 === >> " + executeTime)
    }

    /**
     * 记录失败日志
     */
    private fun failLog(log: ScheduleLog, e: Exception) {
        val executeTime = System.currentTimeMillis() - log.beginTime
        log.times = executeTime.toInt()
        log.status = 1
        log.error = e.message ?: ""
        // 控制台日志记录
        logger.error("任务执行失败，原因：${e.message}", e)
    }

    companion object {
        private val logger = LoggerFactory.getLogger("ScheduleRunnerLog")
    }
}
