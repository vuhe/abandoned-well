package top.vuhe.admin.api.extra

import java.util.*

/**
 * 将字符转换为中国标准的大写
 *
 * @receiver 转换前的字符串
 * @return 转换后的字符串
 */
fun String.localeUppercase(): String = this.uppercase(Locale.CHINA)

/**
 * 将字符转换为中国标准的小写
 *
 * @receiver 转换前的字符串
 * @return 转换后的字符串
 */
fun String.localeLowercase(): String = this.lowercase(Locale.CHINA)
