package top.vuhe.admin.api.jackson

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal

inline fun <reified T : Temporal> JavaTimeModule.addSerializer(pattern: String) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    when (T::class) {
        LocalDateTime::class -> addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
        LocalDate::class -> addSerializer(LocalDate::class.java, LocalDateSerializer(formatter))
        LocalTime::class -> addSerializer(LocalTime::class.java, LocalTimeSerializer(formatter))
        else -> throw UnsupportedOperationException("Unsupported temporal type!")
    }
}
