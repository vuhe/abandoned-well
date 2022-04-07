package top.vuhe.admin.spring.web.controller

import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.spring.web.response.ResultTable
import top.vuhe.admin.spring.web.response.ResultTree

/**
 * ### 统一响应 Controller
 *
 * @author vuhe
 */
abstract class BaseController {

    /**
     * bool 处理结果
     */
    protected fun boolResult(block: () -> Boolean): ResultObj<*> {
        return if (block()) {
            ResultObj.Success<Nothing>()
        } else ResultObj.Fail<Nothing>()
    }

    /**
     * bool 处理结果
     */
    protected fun boolResult(success: String, failure: String, block: () -> Boolean): ResultObj<*> {
        return if (block()) {
            ResultObj.Success<Nothing>(message = success)
        } else ResultObj.Fail<Nothing>(message = failure)
    }

    /**
     * 返回 Tree 数据
     */
    protected fun dataTree(block: () -> Any) = ResultTree(block())

    /**
     * 返回数据表格数据 分页
     */
    protected fun <T : Any> pageTable(block: () -> TablePage<T>): ResultTable {
        val page = block()
        return ResultTable(page.list, page.count.toLong())
    }

    /**
     * 返回数据表格数据
     */
    protected fun dataTable(block: () -> List<*>) = ResultTable(block())

    /**
     * 返回树状表格数据 分页
     */
    protected fun treeTable(block: () -> List<*>) = ResultTable(block())
}
