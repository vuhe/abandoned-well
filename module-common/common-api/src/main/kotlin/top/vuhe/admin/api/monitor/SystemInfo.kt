package top.vuhe.admin.api.monitor

import cn.hutool.core.date.DateUtil
import cn.hutool.core.net.NetUtil
import java.lang.management.ManagementFactory
import java.net.InetAddress
import java.time.Duration
import java.time.Instant

/**
 * ## 系统信息
 * 此信息大部分仅在启动时获取即可，因此定义为单例
 *
 * @author vuhe
 */
object SystemInfo {
    private val props = System.getProperties()
    private val startInstant = ManagementFactory.getRuntimeMXBean().startTime

    /** 服务器信息 */
    val server: String
        get() {
            val name = runCatching { InetAddress.getLocalHost().hostName }
                .getOrDefault("未知")
            val ip = NetUtil.getLocalhostStr() ?: "未知"
            return "$name ($ip)"
        }

    /** 项目路径 */
    val userDir: String = props.getProperty("user.dir")

    /** 操作系统 */
    val osName: String = props.getProperty("os.name")

    /** 系统架构 */
    val osArch: String = props.getProperty("os.arch")

    /** Jvm 名称 */
    val jvmName: String = ManagementFactory.getRuntimeMXBean().vmName

    /** Jvm 版本 */
    val jvmVersion = "Java ${props.getProperty("java.version")}"

    /** Java Home 路径 */
    val javaHome: String = props.getProperty("java.home")

    /** Jvm 启动时间 */
    val jvmStartTime: String by lazy {
        DateUtil.formatDateTime(java.util.Date(startInstant))
    }

    /** Jvm 运行时间 */
    val jvmRunTime: String
        get() {
            val start = Instant.ofEpochMilli(startInstant)
            val end = Instant.now()
            val d = Duration.between(start, end)
            return "${d.toDaysPart()}天${d.toHoursPart()}小时${d.toMinutesPart()}分钟"
        }
}
