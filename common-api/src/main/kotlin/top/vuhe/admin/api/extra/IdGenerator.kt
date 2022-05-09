package top.vuhe.admin.api.extra

import cn.hutool.core.util.IdUtil

/**
 * 对 URL友好的唯一字符串 ID 生成器， 可指定长度
 *
 * @param size id 长度
 * @author vuhe
 */
fun nanoId(size: Int): String = IdUtil.nanoId(size)
