package top.vuhe.admin.spring.database.mapper

import org.ktorm.database.Database
import org.ktorm.dsl.AssignmentsBuilder
import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.springframework.beans.factory.annotation.Autowired
import top.vuhe.admin.spring.database.entity.BaseEntity
import top.vuhe.admin.spring.database.mapper.binding.ColumnProperty
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.createInstance

/**
 * 数据库表查询拓展
 *
 * @author vuhe
 */
abstract class BaseMapper<E : BaseEntity>(tableName: String) : BaseTable<E>(tableName) {
    @Autowired
    protected lateinit var database: Database

    private val columnMap = ArrayList<ColumnProperty<*>>()

    /**
     * 对数据列增加 CURD 支持，同时支持创建实体赋值
     *
     * @param default 插入数据时的默认值
     */
    protected fun Column<String>.bind(
        binding: KMutableProperty1<E, String>, default: String = ""
    ) = apply {
        columnMap.add(
            ColumnProperty(this, binding, default, this in primaryKeys)
        )
    }

    /**
     * 对数据列增加 CURD 支持，同时支持创建实体赋值
     *
     * @param default 插入数据时的默认值
     */
    protected fun <C : Any> Column<C>.bind(
        binding: KMutableProperty1<E, C?>, default: C? = null
    ) = apply {
        columnMap.add(
            ColumnProperty(this, binding, default, this in primaryKeys)
        )
    }

    /**
     * 用于插入使用的设置
     * - 不会插入 主键
     * - 不会插入 null
     *
     * @param entity 待插入的实体
     */
    protected fun AssignmentsBuilder.insertSetting(entity: E) {
        columnMap.forEach {
            if (!it.primary) it.insertSet(this, entity)
        }
    }

    /**
     * 用于更新使用的设置
     * - 不会插入 主键
     * - 不会插入 null
     * - 不会插入 空字符串
     *
     * @param entity 待插入的实体
     */
    protected fun AssignmentsBuilder.updateSetting(entity: E) {
        columnMap.forEach {
            if (!it.primary) it.updateSet(this, entity)
        }
    }

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): E {
        // 由于此类中的泛型不会为 Nothing，因此此处不会为 null
        val entity = entityClass!!.createInstance()
        columnMap.forEach { it.entitySet(row, entity) }
        return entity
    }
}
