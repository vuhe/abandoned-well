package top.vuhe.admin.spring.database.service

import top.vuhe.admin.spring.web.request.PageDomain

/**
 * 分页数据
 *
 * @param rowList    未截取的列表
 * @param pageDomain 分页信息
 * @author vuhe
 */
class TablePage<T : Any>(rowList: List<T>, pageDomain: PageDomain) {
    val count = rowList.size
    val list = rowList.asSequence()
        .drop(pageDomain.offset).take(pageDomain.limit).toList()
}
