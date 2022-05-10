package top.vuhe.admin.api.extra

import java.math.BigDecimal
import java.math.RoundingMode

fun Int.exact(): ExactNum = ExactNum(this.toDouble())
fun Long.exact(): ExactNum = ExactNum(this.toDouble())

@JvmInline
value class ExactNum internal constructor(private val num: BigDecimal) {
    internal constructor(num: Double) : this(BigDecimal(num))

    operator fun minus(other: ExactNum) = ExactNum(num.subtract(other.num))
    operator fun minus(other: Long) = ExactNum(num.subtract(other.toBigDecimal()))
    operator fun times(other: Int) = ExactNum(num.multiply(other.toBigDecimal()))
    operator fun div(other: Long) = ExactNum(
        num.divide(other.toBigDecimal(), 10, RoundingMode.HALF_UP)
    )

    fun round(scale: Int) = ExactNum(num.setScale(scale, RoundingMode.HALF_UP))

    override fun toString(): String = num.toString()
}
