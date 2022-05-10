package top.vuhe.admin.api.extra

import java.text.SimpleDateFormat

private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

/**
 * 格式化日期时间
 *
 * 格式 yyyy-MM-dd HH:mm:ss
 *
 * @param instant 被格式化的时间
 * @return 格式化后的日期
 */
internal fun formatInstant(instant: Long): String {
    return formatter.format(java.util.Date(instant))
}
