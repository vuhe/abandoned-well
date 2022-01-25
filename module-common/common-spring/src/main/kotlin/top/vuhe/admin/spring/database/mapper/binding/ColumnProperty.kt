package top.vuhe.admin.spring.database.mapper.binding

import org.ktorm.dsl.AssignmentsBuilder
import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.Column
import org.slf4j.LoggerFactory
import top.vuhe.admin.spring.database.entity.BaseEntity
import kotlin.reflect.KMutableProperty1

/**
 * 用于记录 colum -> property 的映射
 *
 * @author vuhe
 */
class ColumnProperty<C : Any>(
    val column: Column<C>,
    val property: KMutableProperty1<*, *>,
    val default: C?,
    val primary: Boolean = false
) {
    @Suppress("UNCHECKED_CAST")
    fun <E : BaseEntity> insertSet(sql: AssignmentsBuilder, entity: E) = checker {
        property as KMutableProperty1<E, C?>
        val value = property.get(entity) ?: default
        if (value != null) {
            sql.set(column, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <E : BaseEntity> updateSet(sql: AssignmentsBuilder, entity: E) = checker {
        property as KMutableProperty1<E, C?>
        val value = property.get(entity)
        if (value != null) {
            // 更新时，string 为非空串插入
            // 其它值，非 null 插入
            if (value !is String) {
                sql.set(column, value)
            } else if (value.isNotEmpty()) {
                sql.set(column, value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <E : BaseEntity> entitySet(row: QueryRowSet, entity: E) = checker {
        property as KMutableProperty1<E, Any?>
        val value = when (property.returnType.classifier) {
            // 字段为 string 赋默认值 ""
            String::class -> row[column] ?: ""
            // 其它字段直接取值
            else -> row[column]
        } ?: return@checker
        // 赋非 null 值
        property.set(entity, value)
    }

    private fun checker(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            log.debug("赋值失败！", e)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
