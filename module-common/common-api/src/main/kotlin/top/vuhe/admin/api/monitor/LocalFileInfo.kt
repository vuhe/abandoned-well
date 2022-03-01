package top.vuhe.admin.api.monitor

import cn.hutool.core.util.NumberUtil
import oshi.software.os.OSFileStore

class LocalFileInfo(fs: OSFileStore) {
    private val freeNum = fs.usableSpace
    private val totalNum = fs.totalSpace

    /** 盘符路径 */
    val dirName: String = fs.mount

    /** 盘符类型 */
    val sysTypeName: String = fs.type

    /** 文件类型 */
    val typeName: String = fs.name

    /** 总大小 */
    val total: String = convertFileSize(totalNum)

    /** 剩余大小 */
    val free: String = convertFileSize(freeNum)

    /** 已经使用量 */
    val used: String = convertFileSize(totalNum - freeNum)

    /** 资源的使用率 */
    val usage: String = if (totalNum == 0L) {
        "0.0 %"
    } else {
        NumberUtil.mul(
            NumberUtil.div(totalNum - freeNum, totalNum, 4), 100f
        ).toString() + " %"
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    fun convertFileSize(size: Long): String {
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
