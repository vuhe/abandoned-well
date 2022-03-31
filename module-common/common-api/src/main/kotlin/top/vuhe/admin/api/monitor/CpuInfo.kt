package top.vuhe.admin.api.monitor

import cn.hutool.core.date.DateUtil
import cn.hutool.core.net.NetUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.system.oshi.OshiUtil
import java.lang.management.ManagementFactory
import java.net.InetAddress
import java.time.Duration
import java.time.Instant

@Suppress("MemberVisibilityCanBePrivate", "unused")
class CpuInfo {
    /** 磁盘相关信息 */
    val sysFiles = OshiUtil.getOs().fileSystem.fileStores.asSequence()
        .take(3)
        .map { LocalFileInfo(it) }
        .toList()

    /**
     * 內存相关信息
     */
    val memInfo = MemInfo()

    /**
     * 核心数
     */
    val cpuNum: Int

    /**
     * CPU总的使用率
     */
    val total: Double

    /**
     * CPU系统使用率
     */
    val sys: Double

    /**
     * CPU用户使用率
     */
    val used: Double

    /**
     * CPU当前等待率
     */
    val wait: Double

    /**
     * CPU当前空闲率
     */
    val free: Double

    init {
        val info = OshiUtil.getCpuInfo()
        cpuNum = info.cpuNum
        total = info.toTal
        sys = info.sys
        used = info.used
        wait = info.wait
        free = info.wait
    }

    /**
     * 服务器名称
     */
    val sysInfoComputerName: String = try {
        InetAddress.getLocalHost().hostName
    } catch (ignored: Exception) {
        "未知"
    }

    /**
     * 服务器Ip
     */
    val sysInfoComputerIp: String

    /**
     * 项目路径
     */
    val sysInfoUserDir: String

    /**
     * 操作系统
     */
    val sysInfoOsName: String

    /**
     * 系统架构
     */
    val sysInfoOsArch: String

    init {
        val props = System.getProperties()
        sysInfoComputerIp = NetUtil.getLocalhostStr()
        sysInfoOsName = props.getProperty("os.name")
        sysInfoOsArch = props.getProperty("os.arch")
        sysInfoUserDir = props.getProperty("user.dir")
    }

    /**
     * 当前JVM占用的内存总数(M)
     */
    val jvmInfoTotal = NumberUtil.div(
        Runtime.getRuntime().totalMemory().toDouble(),
        (1024 * 1024).toFloat(), 2
    )

    /**
     * JVM最大可用内存总数(M)
     */
    val jvmInfoMax = NumberUtil.div(
        Runtime.getRuntime().maxMemory().toDouble(),
        (1024 * 1024).toFloat(), 2
    )

    /**
     * JVM空闲内存(M)
     */
    val jvmInfoFree = NumberUtil.div(
        Runtime.getRuntime().freeMemory().toDouble(),
        (1024 * 1024).toFloat(), 2
    )

    /**
     * JDK版本
     */
    val jvmInfoVersion: String

    /**
     * JDK路径
     */
    val jvmInfoHome: String

    init {
        val props = System.getProperties()
        jvmInfoVersion = props.getProperty("java.version")
        jvmInfoHome = props.getProperty("java.home")
    }

    val jvmUsed = NumberUtil.div(jvmInfoTotal - jvmInfoFree, (1024 * 1024).toFloat(), 2)

    val jvmUsage = NumberUtil.mul(NumberUtil.div(jvmInfoTotal - jvmInfoFree, jvmInfoTotal, 4), 100f)

    /**
     * 获取JDK名称
     */
    val jvmInfoName: String = ManagementFactory.getRuntimeMXBean().vmName

    /**
     * JDK启动时间
     */
    val jvmInfoStartTime: String by lazy {
        val time = ManagementFactory.getRuntimeMXBean().startTime
        val date = java.util.Date(time)
        DateUtil.formatDateTime(date)
    }

    /**
     * JDK运行时间
     */
    val jvmInfoRunTime: String by lazy {
        val start = Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().startTime)
        val end = Instant.now()
        val d = Duration.between(start, end)
        "${d.toDaysPart()}天${d.toHoursPart()}小时${d.toMinutesPart()}分钟"
    }
}
