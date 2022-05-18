package top.vuhe.extra

import java.math.BigDecimal
import java.math.RoundingMode

fun Int.exact(): BigDecimal = BigDecimal(toDouble())
fun Long.exact(): BigDecimal = BigDecimal(toDouble())

operator fun BigDecimal.minus(other: Long) = this - other.toBigDecimal()
operator fun BigDecimal.times(other: Int) = this * other.toBigDecimal()
operator fun BigDecimal.div(other: Long): BigDecimal =
    divide(other.toBigDecimal(), 10, RoundingMode.HALF_UP)

fun BigDecimal.round(scale: Int): BigDecimal = setScale(scale, RoundingMode.HALF_UP)
