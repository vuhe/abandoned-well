package top.vuhe.admin.spring.dsl

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import kotlin.reflect.KClass

fun javaTimeModule(block: JavaTimeModuleDsl.() -> Unit): JavaTimeModule {
    return JavaTimeModuleDsl().apply(block).build()
}

class JavaTimeModuleDsl {
    private val module = JavaTimeModule()

    infix fun <T : Temporal> KClass<T>.byPattern(pattern: String) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        when (this) {
            LocalDateTime::class -> module.addSerializer(LocalDateTimeSerializer(formatter))
            LocalDate::class -> module.addSerializer(LocalDateSerializer(formatter))
            LocalTime::class -> module.addSerializer(LocalTimeSerializer(formatter))
            else -> throw UnsupportedOperationException("Unsupported temporal type!")
        }
    }

    fun build(): JavaTimeModule = module
}
