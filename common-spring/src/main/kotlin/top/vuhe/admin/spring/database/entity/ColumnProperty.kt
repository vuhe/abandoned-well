package top.vuhe.admin.spring.database.entity

import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.expression.BinaryExpression
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.SqlType
import org.ktorm.schema.TextSqlType
import org.ktorm.schema.VarcharSqlType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class ColumnProperty<V>(
    internal val column: String,
    internal val sqlType: SqlType<*>,
    private val default: () -> V
) : ReadWriteProperty<Any, V> {
    internal var isPrimary: Boolean = false
    internal var isChanged: Boolean = false
    internal var isNullable: Boolean = false
    internal var beforeChange: ((V) -> V)? = null
    private var value: V? = null

    internal val insertValue: Any?
        get() {
            val v = value ?: default()
            return if (beforeChange != null) {
                beforeChange?.invoke(v)
            } else v
        }

    internal val updateValue: Any?
        get() {
            if (value == null) return null
            return if (beforeChange != null) {
                beforeChange?.invoke(value!!)
            } else value
        }

    internal var rawValue: Any?
        get() = value
        set(value) {
            this.value = value as V?
        }

    internal fun sqlOp(column: ColumnDeclaring<*>): BinaryExpression<Boolean> {
        return if (!isPrimary && (sqlType == VarcharSqlType || sqlType == TextSqlType)) {
            column like (value as String)
        } else (column as ColumnDeclaring<Any>) eq (value as Any)
    }

    /*--------------------------------------- open api -----------------------------------------*/

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        return if (isNullable) value as V
        else requireNotNull(value)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        isChanged = true
        this.value = value
    }

    fun primary(): ColumnProperty<V> {
        isPrimary = true
        return this
    }

    fun nullable(): ColumnProperty<V?> {
        isNullable = true
        return this as ColumnProperty<V?>
    }

    /**
     * 在插入数据库前会调用此方法
     */
    fun insertBefore(block: (V) -> V): ColumnProperty<V> {
        beforeChange = block
        return this
    }
}
