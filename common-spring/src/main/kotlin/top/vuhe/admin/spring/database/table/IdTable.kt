package top.vuhe.admin.spring.database.table

import org.ktorm.entity.Entity
import org.ktorm.schema.Column
import org.ktorm.schema.Table

abstract class IdTable<E : Entity<E>>(tableName: String) : Table<E>(tableName) {
    abstract val id: Column<String>
}
