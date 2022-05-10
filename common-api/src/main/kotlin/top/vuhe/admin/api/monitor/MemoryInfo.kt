package top.vuhe.admin.api.monitor

import top.vuhe.admin.api.extra.div
import top.vuhe.admin.api.extra.exact
import top.vuhe.admin.api.extra.round
import top.vuhe.admin.api.extra.times

/**
 * ## JVM内存信息
 *
 * 脱离虚拟机的内存信息，对项目调参参考意义不大
 *
 * @author vuhe
 */
object MemoryInfo {
    /** 运行时对象 */
    private val runtime: Runtime = Runtime.getRuntime()

    /** Jvm 最大内存总量 */
    val max get() = runtime.maxMemory().toMB()

    /** Jvm 已占用内存总量 */
    val total get() = runtime.totalMemory().toMB()

    /** Jvm 剩余未占用内存 */
    val free get() = runtime.freeMemory().toMB()

    /** 内存使用百分比 */
    val usage get() = usage()

    private fun Long.toMB(): String {
        val num = exact() / (1024 * 1024)
        return "${num.round(2)} M"
    }

    private fun usage(): String {
        val used = runtime.totalMemory().exact()
        val total = runtime.maxMemory()
        val num = used / total * 100
        return "${num.round(2)} %"
    }
}
