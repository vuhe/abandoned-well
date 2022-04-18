package top.vuhe.admin.spring.database.table

/**
 * 分页数据
 *
 * @author vuhe
 */
data class TablePage<T : Any> internal constructor(val count: Int, val list: List<T>)
