package top.vuhe.admin.api.monitor

import cn.hutool.system.oshi.OshiUtil

/**
 * ## 系统监控信息
 *
 * @author vuhe
 */
object MonitorInfo {
    val cpu = CpuInfo
    val memory: MemoryInfo get() = MemoryInfo()
    val system = SystemInfo
    val disk: List<DiskInfo> get() = findDiskInfo()

    private fun findDiskInfo(): List<DiskInfo> {
        return OshiUtil.getOs().fileSystem.fileStores.asSequence()
            .take(4)
            .map { DiskInfo(it) }
            .toList()
    }
}
