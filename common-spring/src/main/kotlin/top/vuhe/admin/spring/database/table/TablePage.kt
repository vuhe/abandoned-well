package top.vuhe.admin.spring.database.table

/**
 * 分页数据
 *
 * @author vuhe
 */
data class TablePage<T : Any>(val count: Int, val list: List<T>)
