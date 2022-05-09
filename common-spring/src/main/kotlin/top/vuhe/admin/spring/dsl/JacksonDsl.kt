package top.vuhe.admin.spring.dsl

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class DatetimeModule : SimpleModule() {
    override fun getDependencies(): Iterable<Module> = listOf(JavaTimeModule())

    @JvmName("byPatternLocalDateTime")
    infix fun KClass<LocalDateTime>.byPattern(pattern: String) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        addSerializer(java, LocalDateTimeSerializer(formatter))
    }

    @JvmName("byPatternLocalDate")
    infix fun KClass<LocalDate>.byPattern(pattern: String) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        addSerializer(java, LocalDateSerializer(formatter))
    }

    @JvmName("byPatternLocalTime")
    infix fun KClass<LocalTime>.byPattern(pattern: String) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        addSerializer(java, LocalTimeSerializer(formatter))
    }

    companion object {
        inline operator fun invoke(setting: DatetimeModule.() -> Unit): DatetimeModule {
            return DatetimeModule().apply(setting)
        }
    }
}
