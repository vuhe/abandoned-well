package top.vuhe.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DatetimeModule : SimpleModule() {
    private val datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    init {
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(datetimeFormatter))
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(datetimeFormatter))

        addSerializer(LocalDate::class.java, LocalDateSerializer(dateFormatter))
        addDeserializer(LocalDate::class.java, LocalDateDeserializer(dateFormatter))

        addSerializer(LocalTime::class.java, LocalTimeSerializer(timeFormatter))
        addDeserializer(LocalTime::class.java, LocalTimeDeserializer(timeFormatter))
    }

    override fun getDependencies() = listOf(JavaTimeModule())
}
