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
    protected inline fun boolResult(block: () -> Boolean): ResultObj<*> {
        return if (block()) {
            ResultObj.Success<Nothing>()
        } else ResultObj.Fail<Nothing>()
    }

    /**
     * bool 处理结果
     */
    protected inline fun boolResult(
        success: String = "修改成功", failure: String = "修改失败", block: () -> Boolean
    ): ResultObj<*> = if (block()) {
        ResultObj.Success<Nothing>(message = success)
    } else ResultObj.Fail<Nothing>(message = failure)

    /**
     * 返回 Tree 数据
     */
    protected inline fun dataTree(block: () -> Any) = ResultTree(block())

    /**
     * 返回数据表格数据 分页
     */
    protected inline fun <T : Any> pageTable(block: () -> TablePage<T>): ResultTable {
        val page = block()
        return ResultTable(page.list, page.count.toLong())
    }

    /**
     * 返回数据表格数据
     */
    protected inline fun dataTable(block: () -> List<*>) = ResultTable(block())

    /**
     * 返回树状表格数据 分页
     */
    protected inline fun treeTable(block: () -> List<*>) = ResultTable(block())
}
