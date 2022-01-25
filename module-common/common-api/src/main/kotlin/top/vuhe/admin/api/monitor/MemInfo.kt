package top.vuhe.admin.api.monitor

import cn.hutool.core.util.NumberUtil

class MemInfo {
    /**
     * 内存总量
     */
    var total = 0.0
        get() = NumberUtil.div(field, (1024 * 1024 * 1024).toFloat(), 2)

    /**
     * 已用内存
     */
    var used = 0.0
        get() = NumberUtil.div(field, (1024 * 1024 * 1024).toFloat(), 2)

    /**
     * 剩余内存
     */
    var free = 0.0
        get() = NumberUtil.div(field, (1024 * 1024 * 1024).toFloat(), 2)

    val usage: Double
        get() = NumberUtil.mul(NumberUtil.div(used, total, 4), 100f)
}
