package top.vuhe.admin.spring.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.ktorm.schema.*
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * ### 基础实体类
 *
 * @author vuhe
 */
abstract class BaseEntity {
    @JsonIgnore
    internal val properties = ArrayList<ColumnProperty<*>>()

    protected fun varchar(column: String) =
        ColumnProperty(column, VarcharSqlType) { "" }.also {
            properties.add(it)
        }

    protected fun text(column: String) =
        ColumnProperty(column, TextSqlType) { "" }.also {
            properties.add(it)
        }

    protected fun boolean(column: String, default: Boolean = false) =
        ColumnProperty(column, BooleanSqlType) { default }.also {
            properties.add(it)
        }

    protected fun int(column: String, default: Int = 0) =
        ColumnProperty(column, IntSqlType) { default }.also {
            properties.add(it)
        }

    protected fun datetime(column: String) =
        ColumnProperty<LocalDateTime>(column, LocalDateTimeSqlType) {
            LocalDateTime.now()
        }.also { properties.add(it) }

    protected fun date(column: String) =
        ColumnProperty<LocalDate>(column, LocalDateSqlType) {
            LocalDate.now()
        }.also { properties.add(it) }

    protected inline fun <reified C : Enum<C>> enum(column: String, default: C) =
        enum(column, default, C::class.java)

    protected fun <C : Enum<C>> enum(column: String, default: C, clazz: Class<C>) =
        ColumnProperty(column, EnumSqlType(clazz)) { default }.also {
            properties.add(it)
        }
}
