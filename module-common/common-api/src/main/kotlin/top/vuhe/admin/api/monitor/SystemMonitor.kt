package top.vuhe.admin.api.monitor

import cn.hutool.core.net.NetUtil
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory
import oshi.util.Util
import top.vuhe.admin.api.file.FileOperatorApi
import top.vuhe.admin.api.spring.spring
import java.net.InetAddress

/**
 * 系统监控工具
 *
 * @author vuhe
 */
object SystemMonitor {
    private const val WAIT_SECOND = 110L
    private val fileOperator: FileOperatorApi by spring()

    val cpu: CpuInfo
        get() {
            val hal = SystemInfo().hardware
            val cpu = CpuInfo().apply {
                setSysInfo()
                setJvmInfo()
                setCpuInfo(hal.processor)
                memInfo = setMemInfo(hal.memory)
                sysFiles = fileOperator.fileInfos
            }
            return cpu
        }

    private fun CpuInfo.setSysInfo() {
        val props = System.getProperties()
        sysInfoComputerName = hostName
        sysInfoComputerIp = NetUtil.getLocalhostStr()
        sysInfoOsName = props.getProperty("os.name")
        sysInfoOsArch = props.getProperty("os.arch")
        sysInfoUserDir = props.getProperty("user.dir")
    }

    /**
     * 获取客户端主机名称
     */
    private val hostName: String
        get() = try {
            InetAddress.getLocalHost().hostName
        } catch (ignored: Exception) {
            "未知"
        }

    /**
     * 设置Java虚拟机
     */
    private fun CpuInfo.setJvmInfo() {
        val props = System.getProperties()
        jvmInfoTotal = Runtime.getRuntime().totalMemory().toDouble()
        jvmInfoMax = Runtime.getRuntime().maxMemory().toDouble()
        jvmInfoFree = Runtime.getRuntime().freeMemory().toDouble()
        jvmInfoVersion = props.getProperty("java.version")
        jvmInfoHome = props.getProperty("java.home")
    }

    /**
     * 设置内存信息
     */
    private fun setMemInfo(memory: GlobalMemory) = MemInfo().apply {
        total = memory.total.toDouble()
        used = (memory.total - memory.available).toDouble()
        free = memory.available.toDouble()
    }

    /**
     * 设置CPU信息
     */
    private fun CpuInfo.setCpuInfo(processor: CentralProcessor) {
        // CPU信息
        val prevTicks: LongArray = processor.systemCpuLoadTicks
        Util.sleep(WAIT_SECOND)
        val ticks: LongArray = processor.systemCpuLoadTicks
        val nice =
            ticks[CentralProcessor.TickType.NICE.index] - prevTicks[CentralProcessor.TickType.NICE.index]
        val irq = ticks[CentralProcessor.TickType.IRQ.index] - prevTicks[CentralProcessor.TickType.IRQ.index]
        val softirq =
            ticks[CentralProcessor.TickType.SOFTIRQ.index] - prevTicks[CentralProcessor.TickType.SOFTIRQ.index]
        val steal =
            ticks[CentralProcessor.TickType.STEAL.index] - prevTicks[CentralProcessor.TickType.STEAL.index]
        val cSys =
            ticks[CentralProcessor.TickType.SYSTEM.index] - prevTicks[CentralProcessor.TickType.SYSTEM.index]
        val user =
            ticks[CentralProcessor.TickType.USER.index] - prevTicks[CentralProcessor.TickType.USER.index]
        val iowait =
            ticks[CentralProcessor.TickType.IOWAIT.index] - prevTicks[CentralProcessor.TickType.IOWAIT.index]
        val idle =
            ticks[CentralProcessor.TickType.IDLE.index] - prevTicks[CentralProcessor.TickType.IDLE.index]
        val totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal
        cpuNum = processor.logicalProcessorCount
        total = totalCpu.toDouble()
        sys = cSys.toDouble()
        used = user.toDouble()
        wait = iowait.toDouble()
        free = idle.toDouble()
    }
}
