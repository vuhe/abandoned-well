package top.vuhe.admin.api.monitor

import cn.hutool.core.date.DateUnit
import cn.hutool.core.date.DateUtil
import cn.hutool.core.util.NumberUtil
import top.vuhe.admin.api.file.FileInfo
import java.lang.management.ManagementFactory
import java.util.*

class CpuInfo {
    /**
     * 磁盘相关信息
     */
    var sysFiles: List<FileInfo> = emptyList()

    /**
     * 內存相关信息
     */
    var memInfo: MemInfo? = null

    /**
     * 核心数
     */
    var cpuNum = 0

    /**
     * CPU总的使用率
     */
    var total = 0.0

    /**
     * CPU系统使用率
     */
    var sys = 0.0

    /**
     * CPU用户使用率
     */
    var used = 0.0

    /**
     * CPU当前等待率
     */
    var wait = 0.0

    /**
     * CPU当前空闲率
     */
    var free = 0.0

    /**
     * 服务器名称
     */
    var sysInfoComputerName: String = ""

    /**
     * 服务器Ip
     */
    var sysInfoComputerIp: String = ""

    /**
     * 项目路径
     */
    var sysInfoUserDir: String = ""

    /**
     * 操作系统
     */
    var sysInfoOsName: String = ""

    /**
     * 系统架构
     */
    var sysInfoOsArch: String = ""

    /**
     * 当前JVM占用的内存总数(M)
     */
    var jvmInfoTotal = 0.0
        get() = NumberUtil.div(field, (1024 * 1024).toFloat(), 2)

    /**
     * JVM最大可用内存总数(M)
     */
    var jvmInfoMax = 0.0
        get() = NumberUtil.div(field, (1024 * 1024).toFloat(), 2)

    /**
     * JVM空闲内存(M)
     */
    var jvmInfoFree = 0.0
        get() = NumberUtil.div(field, (1024 * 1024).toFloat(), 2)

    /**
     * JDK版本
     */
    var jvmInfoVersion: String? = null

    /**
     * JDK路径
     */
    var jvmInfoHome: String? = null

    val jvmUsed: Double
        get() = NumberUtil.div(jvmInfoTotal - jvmInfoFree, (1024 * 1024).toFloat(), 2)

    val jvmUsage: Double
        get() = NumberUtil.mul(NumberUtil.div(jvmInfoTotal - jvmInfoFree, jvmInfoTotal, 4), 100f)

    /**
     * 获取JDK名称
     */
    val jvmInfoName: String
        get() = ManagementFactory.getRuntimeMXBean().vmName

    /**
     * JDK启动时间
     */
    val jvmInfoStartTime: String
        get() {
            val time = ManagementFactory.getRuntimeMXBean().startTime
            val date = Date(time)
            return DateUtil.formatDateTime(date)
        }

    /**
     * JDK运行时间
     */
    val jvmInfoRunTime: String
        get() {
            val time = ManagementFactory.getRuntimeMXBean().startTime
            val date = Date(time)
            val runMS: Long = DateUtil.between(date, Date(), DateUnit.MS)
            val nd = (1000 * 24 * 60 * 60).toLong()
            val nh = (1000 * 60 * 60).toLong()
            val nm = (1000 * 60).toLong()
            val day = runMS / nd
            val hour = runMS % nd / nh
            val min = runMS % nd % nh / nm
            return day.toString() + "天" + hour + "小时" + min + "分钟"
        }
}
