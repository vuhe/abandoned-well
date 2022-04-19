package top.vuhe.admin.spring.database.repository

import org.ktorm.entity.Entity
import top.vuhe.admin.spring.database.table.IdTable

/**
 * ## 树结构访问器
 *
 * 在增删改查的基础上，添加了有关树的方法；
 * 此外，本类不能添加必要实体字段，详情见 [BaseRepository]
 */
abstract class TreeRepository<T, E> : CurdRepository<T, E>()
        where E : Entity<E>, T : IdTable<E> {

    /**
     * 计算子节点个数
     */
    abstract fun countChildren(id: String): Int

}
