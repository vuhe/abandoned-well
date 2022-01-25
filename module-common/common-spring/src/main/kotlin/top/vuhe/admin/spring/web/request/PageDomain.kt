package top.vuhe.admin.spring.web.request

/**
 * ### 分页参数封装
 *
 * @author vuhe
 */
class PageDomain {
    /**
     * 当前页
     */
    var page: Int = 1

    /**
     * 每页数量
     */
    var limit: Int = 0

    /**
     * 总数量
     */
    var count: Int = 0

    /**
     * 查询结果
     */
    var list: List<*> = emptyList<Nothing>()

    /**
     * 获取开始的数据行
     */
    val offset: Int get() = (page - 1) * limit

    /**
     * 获取结束的数据行
     */
    val end: Int get() = page * limit
}
