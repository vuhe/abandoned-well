package top.vuhe.admin.api.extra

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun Number.exact(): ExactNum = when (this) {
    is ExactNum -> this
    is BigDecimal -> ExactNum(this)
    is BigInteger -> ExactNum(this)
    else -> ExactNum(this.toDouble())
}

operator fun Number.plus(num: ExactNum): ExactNum = exact() + num
operator fun Number.minus(num: ExactNum): ExactNum = exact() - num
operator fun Number.times(num: ExactNum): ExactNum = exact() * num
operator fun Number.div(num: ExactNum): ExactNum = exact() / num

data class ExactNum internal constructor(private val num: BigDecimal) :
    Number(), Comparable<ExactNum> {
    internal constructor(num: BigInteger) : this(BigDecimal(num))
    internal constructor(num: Double) : this(BigDecimal(num))

    operator fun unaryMinus() = ExactNum(num.negate())

    operator fun plus(other: Number) = ExactNum(num.add(other.exact().num))

    operator fun minus(other: Number) = ExactNum(num.subtract(other.exact().num))

    operator fun times(other: Number) = ExactNum(num.multiply(other.exact().num))

    operator fun div(other: Number): ExactNum {
        val o = other.exact().num
        return ExactNum(num.divide(o, 10, RoundingMode.HALF_UP))
    }

    override fun compareTo(other: ExactNum): Int = num.compareTo(other.num)

    override fun toDouble(): Double = num.toDouble()
    override fun toFloat(): Float = num.toFloat()
    override fun toLong(): Long = num.toLong()
    override fun toInt(): Int = num.toInt()
    override fun toShort(): Short = num.toShort()
    override fun toByte(): Byte = num.toByte()
    override fun toChar(): Char = num.toChar()

    override fun toString(): String = toString(10)
    fun toString(scale: Int): String = num.setScale(scale, RoundingMode.HALF_UP).toString()
}
