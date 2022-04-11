package top.vuhe.admin.api.monitor

import cn.hutool.core.date.DateUtil
import cn.hutool.system.SystemUtil
import kotlin.time.Duration.Companion.milliseconds

/**
 * ## 系统信息
 * 此信息大部分仅在启动时获取即可，因此定义为单例
 *
 * @author vuhe
 */
object SystemInfo {
    private val startInstant = SystemUtil.getRuntimeMXBean().startTime

    /** 服务器信息 */
    val server: String = "${SystemUtil.getHostInfo().name} (${SystemUtil.getHostInfo().address})"

    /** 项目路径 */
    val userDir: String = SystemUtil.getUserInfo().currentDir

    /** 操作系统 */
    val osName: String = SystemUtil.getOsInfo().name

    /** 系统架构 */
    val osArch: String = SystemUtil.getOsInfo().arch

    /** Jvm 名称 */
    val jvmName: String = SystemUtil.getJvmInfo().name

    /** Jvm 版本 */
    val jvmVersion = "Java ${SystemUtil.getJavaInfo().version}"

    /** Java Home 路径 */
    val javaHome: String = SystemUtil.getJavaRuntimeInfo().homeDir

    /** Jvm 启动时间 */
    val jvmStartTime: String by lazy {
        DateUtil.formatDateTime(java.util.Date(startInstant))
    }

    /** Jvm 运行时间 */
    val jvmRunTime: String
        get() {
            val start = startInstant.milliseconds
            val end = System.currentTimeMillis().milliseconds
            return (end - start).toComponents { days, hours, minutes, seconds, _ ->
                StringBuilder().apply {
                    if (days > 0) append("$days 天 ")
                    if (hours > 0) append("$hours 小时 ")
                    if (minutes > 0) append("$minutes 分钟 ")
                    append("$seconds 秒")
                }.toString()
            }
        }
}
