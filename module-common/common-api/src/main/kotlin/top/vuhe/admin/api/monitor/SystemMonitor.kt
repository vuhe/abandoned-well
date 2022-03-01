package top.vuhe.admin.api.monitor

/**
 * 系统监控工具
 *
 * @author vuhe
 */
object SystemMonitor {
    val cpu: CpuInfo get() = CpuInfo()
}
