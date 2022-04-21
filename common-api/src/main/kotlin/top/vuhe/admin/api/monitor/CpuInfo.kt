package top.vuhe.admin.api.monitor

import cn.hutool.system.oshi.OshiUtil

/**
 * ## CPU 信息
 *
 * @author vuhe
 */
object CpuInfo {
    private var info = OshiUtil.getCpuInfo(100)

    /** 核心数 */
    val num get() = "${info.cpuNum} 核"

    /** CPU用户使用率 */
    val used get() = "${info.used} %"

    /** CPU当前等待率 */
    val wait get() = "${info.wait} %"

    /** CPU当前空闲率 */
    val free get() = "${info.free} %"

    /**
     * 更新 CPU 信息
     */
    fun updateInfo() {
        val cpu = OshiUtil.getCpuInfo(5000)
        synchronized(info) { info = cpu }
    }
}
