package top.vuhe.database.service

import org.ktorm.entity.Entity
import top.vuhe.database.repository.TreeRepository

abstract class TreeService<E : Entity<E>> : CurdService<E>() {
    abstract override val repository: TreeRepository<*, E>

    fun isLeafNode(id: String): Boolean = repository.countChildren(id) == 0

}
