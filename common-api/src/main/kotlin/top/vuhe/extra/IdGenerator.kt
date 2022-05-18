package top.vuhe.extra

import java.security.SecureRandom
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.ln

/**
 * NanoId，一个小型、安全、对 URL友好的唯一字符串 ID 生成器，特点：
 * * 安全：它使用加密、强大的随机 API，并保证符号的正确分配
 * * 体积小：只有 258 bytes 大小（压缩后）、无依赖
 * * 紧凑：它使用比 UUID (A-Za-z0-9_~)更多的符号
 *
 * 此实现的逻辑基于JavaScript的[NanoId实现](https://github.com/ai/nanoid)
 *
 * @author David Klebanoff
 * @author vuhe
 */
object NanoId {
    /** 随机数生成器，使用[SecureRandom]确保健壮性 */
    private val random = SecureRandom()

    /** 随机字母表，使用URL安全的Base64字符 */
    @Suppress("SpellCheckingInspection")
    private val alphabet = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

    /**
     * 生成伪随机的NanoId字符串
     *
     * @param size ID长度
     * @return 伪随机的NanoId字符串
     */
    operator fun invoke(size: Int): String {
        require(size > 0) { "Size must be greater than zero." }
        val mask = (2 shl floor(ln((alphabet.size - 1).toDouble()) / ln(2.0)).toInt()) - 1
        val step = ceil(1.6 * mask * size / alphabet.size).toInt()

        return buildString {
            while (length < size) {
                val bytes = ByteArray(step)
                random.nextBytes(bytes)
                bytes.forEach {
                    val alphabetIndex = it.toInt() and mask
                    if (alphabetIndex < alphabet.size) {
                        append(alphabet[alphabetIndex])
                    }
                }
            }
            delete(size, length)
        }
    }
}
