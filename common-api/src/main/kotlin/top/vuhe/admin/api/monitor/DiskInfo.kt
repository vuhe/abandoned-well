package top.vuhe.admin.api.monitor

import cn.hutool.core.util.NumberUtil
import oshi.software.os.OSFileStore

/**
 * ## 磁盘信息
 *
 * @author vuhe
 */
class DiskInfo(fs: OSFileStore) {
    private val freeNum = fs.usableSpace
    private val totalNum = fs.totalSpace

    /** 存储器名称 */
    val name: String = fs.name

    /** 总大小 */
    val total: String = convertFileSize(totalNum)

    /** 剩余大小 */
    val free: String = convertFileSize(freeNum)

    /** 已经使用量 */
    val used: String = convertFileSize(totalNum - freeNum)

    /** 资源的使用率 */
    val usage: String = usage()

    private fun usage(): String {
        if (totalNum == 0L) return "0.0 %"
        val num = NumberUtil.div(totalNum - freeNum, totalNum, 4)
        return "${NumberUtil.mul(num, 100f)} %"
    }

    private fun convertFileSize(size: Long): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024
        return if (size >= gb) {
            String.format("%.1f GB", size.toFloat() / gb)
        } else if (size >= mb) {
            val f = size.toFloat() / mb
            String.format(if (f > 100) "%.0f MB" else "%.1f MB", f)
        } else if (size >= kb) {
            val f = size.toFloat() / kb
            String.format(if (f > 100) "%.0f KB" else "%.1f KB", f)
        } else {
            String.format("%d B", size)
        }
    }
}
