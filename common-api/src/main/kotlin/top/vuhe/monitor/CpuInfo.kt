package top.vuhe.monitor

/**
 * ## CPU 信息
 *
 * @author vuhe
 */
data class CpuInfo(
    /** 核心数 */
    val num: String,
    /** CPU用户使用率 */
    val used: String,
    /** CPU当前等待率 */
    val wait: String,
    /** CPU当前空闲率 */
    val free: String
)
