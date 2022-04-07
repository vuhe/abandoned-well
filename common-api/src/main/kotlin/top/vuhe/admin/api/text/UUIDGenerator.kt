package top.vuhe.admin.api.text

import cn.hutool.core.util.IdUtil

/**
 * UUID 生成器
 *
 * @author vuhe
 */
object UUIDGenerator {
    /**
     * 对 URL友好的唯一字符串 ID 生成器，
     * 可指定长度
     */
    fun create(size: Int): String = IdUtil.nanoId(size)
}
