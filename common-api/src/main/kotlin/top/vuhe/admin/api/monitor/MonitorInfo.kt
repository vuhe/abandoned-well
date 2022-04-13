package top.vuhe.admin.api.monitor

import cn.hutool.system.oshi.OshiUtil
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * ## 系统监控信息
 *
 * @author vuhe
 */
object MonitorInfo {
    val cpu = CpuInfo
    val memory = MemoryInfo
    val system = SystemInfo
    val disk: List<DiskInfo> get() = findDiskInfo()

    /**
     * 刷新 Cpu 信息
     */
    fun refresh() {
        ScheduledThreadPoolExecutor(1) { r ->
            Thread(r).apply {
                name = "cpuInfoUpdater"
                isDaemon = true
            }
        }.scheduleWithFixedDelay({
            val info = OshiUtil.getCpuInfo(5000)
            CpuInfo.updateInfo(info)
        }, 0, 60, TimeUnit.SECONDS)
    }

    private fun findDiskInfo(): List<DiskInfo> {
        return OshiUtil.getOs().fileSystem.fileStores.asSequence()
            .take(4)
            .map { DiskInfo(it) }
            .toList()
    }
}
