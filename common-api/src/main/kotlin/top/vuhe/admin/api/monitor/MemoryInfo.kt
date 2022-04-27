package top.vuhe.admin.api.monitor

import cn.hutool.system.SystemUtil
import top.vuhe.admin.api.extra.exact

/**
 * ## JVM内存信息
 * 脱离虚拟机的内存信息，对项目调参参考意义不大
 *
 * @author vuhe
 */
object MemoryInfo {
    /** Jvm 最大内存总量 */
    val max = SystemUtil.getMaxMemory().toMB()

    /** Jvm 已占用内存总量 */
    val total = SystemUtil.getTotalMemory().toMB()

    /** Jvm 剩余未占用内存 */
    val free = SystemUtil.getFreeMemory().toMB()

    /** 内存使用百分比 */
    val usage get() = usage()

    private fun Long.toMB(): String {
        val num = exact() / (1024 * 1024)
        return "${num.toString(2)} M"
    }

    private fun usage(): String {
        val used = SystemUtil.getTotalMemory().toDouble().exact()
        val total = SystemUtil.getMaxMemory().toDouble()
        val num = used / total * 100
        return "${num.toString(2)} %"
    }
}
