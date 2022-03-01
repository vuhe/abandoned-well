package top.vuhe.admin.api.monitor

import cn.hutool.core.util.NumberUtil
import cn.hutool.system.oshi.OshiUtil

class MemInfo {
    private val memory = OshiUtil.getMemory()

    /**
     * 内存总量
     */
    val total = NumberUtil.div(
        memory.total.toDouble(),
        (1024 * 1024 * 1024).toFloat(), 2
    )

    /**
     * 已用内存
     */
    val used = NumberUtil.div(
        (memory.total - memory.available).toDouble(),
        (1024 * 1024 * 1024).toFloat(), 2
    )

    /**
     * 剩余内存
     */
    val free = NumberUtil.div(
        memory.available.toDouble(),
        (1024 * 1024 * 1024).toFloat(), 2
    )

    val usage = NumberUtil.mul(NumberUtil.div(used, total, 4), 100f)
}
