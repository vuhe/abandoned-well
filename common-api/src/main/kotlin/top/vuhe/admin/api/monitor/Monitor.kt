package top.vuhe.admin.api.monitor

import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * ## 系统监控信息
 *
 * @author vuhe
 */
object Monitor {
    /** CPU 信息 */
    var cpu = cpuInfo(100.milliseconds)
        private set

    /** JVM内存信息 */
    val memory = MemoryInfo

    /** 系统环境信息 */
    val system = EnvInfo

    /** 文件系统信息 */
    val disk: List<DiskInfo> get() = HardwareInfo.fileStores.take(4)

    /** 刷新监控信息 */
    fun refresh() = synchronized(cpu) { cpu = cpuInfo(1.seconds) }
}
