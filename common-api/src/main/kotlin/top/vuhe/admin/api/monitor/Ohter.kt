package top.vuhe.admin.api.monitor

import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.software.os.OperatingSystem
import oshi.util.Util
import top.vuhe.admin.api.extra.div
import top.vuhe.admin.api.extra.exact
import top.vuhe.admin.api.extra.round
import top.vuhe.admin.api.extra.times
import java.math.BigDecimal
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import kotlin.time.Duration

internal fun cpuInfo(waitingTime: Duration): CpuInfo {
    val processor = HardwareInfo.processor
    val ticks = tick(processor, waitingTime.inWholeMilliseconds)
    val free = ticks.idle per ticks.totalCpu
    return CpuInfo(
        num = "${processor.logicalProcessorCount} 核",
        wait = "${ticks.ioWait per ticks.totalCpu} %",
        free = "$free %",
        used = "${100.exact() - free} %",
    )
}

/** 获取每个CPU核心的tick，计算方式为 100 * tick / totalCpu */
private infix fun Long.per(totalCpu: Long): BigDecimal {
    if (totalCpu == 0L || this <= 0) return BigDecimal.ZERO
    return (exact() * 100 / totalCpu).round(2)
}

private fun tick(processor: CentralProcessor, waitingTime: Long): CpuTicks {
    val start = processor.systemCpuLoadTicks
    Util.sleep(waitingTime)
    val end = processor.systemCpuLoadTicks
    // 此处调用的 api 规定了数组大小为 8
    // 相同索引直接相减即可
    val list = (0 until 8).map { end[it] - start[it] }
    return CpuTicks(list)
}

/**
 * 获取本机网卡IP地址，规则如下：
 * 1. 查找所有网卡地址，必须非回路（loopback）地址、非局域网地址（siteLocal）、IPv4地址
 * 2. 如果无满足要求的地址，调用 [InetAddress.getLocalHost] 获取地址
 */
private fun localhost(): InetAddress? {
    val localAddressList = runCatching {
        NetworkInterface.getNetworkInterfaces().asSequence()
    }.getOrDefault(emptySequence())
        .filterNotNull()
        .flatMap { it.inetAddresses.asSequence() }
        .filterNotNull()
        // 需为IPV4地址
        .filter { (!it.isLoopbackAddress && it is Inet4Address) }
        .toSet()

    // 非地区本地地址，指
    // 10.0.0.0 ~ 10.255.255.255、
    // 172.16.0.0 ~ 172.31.255.255、
    // 192.168.0.0 ~ 192.168.255.255
    localAddressList.find { !it.isSiteLocalAddress }?.let { return it }

    // 全为本地区的，返回第一个
    localAddressList.firstOrNull()?.let { return it }

    // 以上全不存在的话，最后尝试
    return runCatching { InetAddress.getLocalHost() }.getOrNull()
}

/** CPU负载时间信息 */
private class CpuTicks(array: List<Long>) {
    val idle: Long = array[CentralProcessor.TickType.IDLE.index]
    val nice: Long = array[CentralProcessor.TickType.NICE.index]
    private val irq: Long = array[CentralProcessor.TickType.IRQ.index]
    private val softIrq: Long = array[CentralProcessor.TickType.SOFTIRQ.index]
    private val steal: Long = array[CentralProcessor.TickType.STEAL.index]
    private val cSys: Long = array[CentralProcessor.TickType.SYSTEM.index]
    val user: Long = array[CentralProcessor.TickType.USER.index]
    val ioWait: Long = array[CentralProcessor.TickType.IOWAIT.index]

    /** CPU总的使用率 */
    val totalCpu: Long = (user + nice + cSys + idle + ioWait + irq + softIrq + steal)
        .coerceAtLeast(0)
}

/** 硬件信息，由 OsHI 提供实现 */
object HardwareInfo {
    private val systemInfo = SystemInfo()

    /** 操作系统相关信息，包括系统版本、文件系统、进程等 */
    private val os: OperatingSystem = systemInfo.operatingSystem

    /** 文件系统相关信息 */
    val fileStores = os.fileSystem.fileStores.map { DiskInfo(it) }

    /** 硬件相关信息，包括内存、硬盘、网络设备、显示器、USB、声卡等 */
    private val hardware = systemInfo.hardware

    /** CPU（处理器）相关信息，比如CPU负载等 */
    val processor: CentralProcessor get() = hardware.processor
}

/** 代表当前主机的信息 */
object HostInfo {
    private val localhost: InetAddress? by lazy { localhost() }

    /** 当前主机的名称, 例如: `webserver1` */
    val name: String = localhost?.hostName ?: "Unknown"

    /** 当前主机的地址, 例如: `192.168.0.1` */
    val address: String = localhost?.hostAddress ?: "Unknown"

    override fun toString(): String = "$name ($address)"
}
