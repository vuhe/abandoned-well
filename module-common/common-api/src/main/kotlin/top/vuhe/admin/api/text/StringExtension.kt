package top.vuhe.admin.api.text

import cn.hutool.core.util.StrUtil
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

/**
 * 通过限定左右字符串截取
 *
 * @receiver 待截取的字符串
 * @return 截取后的字符串，无法截取的返回 ""
 */
fun String.substring(left: String, right: String): String =
    StrUtil.subBetween(left, right) ?: ""

/**
 * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
 *
 * 例如：hello_world => helloWorld
 *
 * @receiver 转换前的下划线大写方式命名的字符串
 * @return 转换后的驼峰式命名的字符串
 */
fun String.toCamelCase(): String = StrUtil.toCamelCase(this)
