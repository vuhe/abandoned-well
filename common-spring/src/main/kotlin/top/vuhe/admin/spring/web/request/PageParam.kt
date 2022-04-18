package top.vuhe.admin.spring.web.request

/**
 * ### 分页参数封装
 *
 * @author vuhe
 */
abstract class PageParam {
    /**
     * 当前页
     */
    var page: Int = 1

    /**
     * 每页数量
     */
    var limit: Int = 0

    /**
     * 获取开始的数据行
     */
    val offset: Int get() = (page - 1) * limit
}