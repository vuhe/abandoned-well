package top.vuhe.admin.api.monitor

import java.lang.management.ManagementFactory
import java.text.SimpleDateFormat
import kotlin.time.Duration.Companion.milliseconds

/**
 * ## 环境信息
 *
 * 此信息大部分仅在启动时获取即可，因此定义为单例
 *
 * @author vuhe
 */
object EnvInfo {
    private val startInstant = ManagementFactory.getRuntimeMXBean().startTime

    /** 服务器信息 */
    val server: String = HostInfo.toString()

    /** 项目路径 */
    val userDir: String = this["user.dir"]

    /** 操作系统 */
    val osName: String = this["os.name"]

    /** 系统架构 */
    val osArch: String = this["os.arch"]

    /** Jvm 名称 */
    val jvmName: String = this["java.vm.name"]

    /** Jvm 版本 */
    val jvmVersion = "Java ${this["java.version"]}"

    /** Java Home 路径 */
    val javaHome: String = this["java.home"]

    /** Jvm 启动时间 */
    val jvmStartTime: String by lazy {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.format(java.util.Date(startInstant))
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

    /**
     * 取得系统属性
     *
     * @param name 属性名
     * @return 属性值或 `Unknown`
     * @see System.getProperty
     * @see System.getenv
     */
    private operator fun get(name: String): String {
        kotlin.runCatching { return System.getProperty(name) }
        return kotlin.runCatching { System.getProperty(name) }
            .getOrDefault("Unknown")
    }
}
