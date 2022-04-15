package top.vuhe.admin.spring.web.controller

import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.spring.web.response.ResultTable
import top.vuhe.admin.spring.web.response.ResultTree

/**
 * ### 统一响应 Controller
 *
 * @author vuhe
 */
abstract class BaseController {

    protected fun success(message: String = "操作成功", data: Any? = null) = ResultObj.Success(message, data)

    protected fun fail(code: Int = 500, message: String = "操作失败") = ResultObj.Fail(code, message)

    /**
     * bool 处理结果
     */
    protected inline fun boolResult(block: () -> Boolean): ResultObj {
        return if (block()) success()
        else fail()
    }

    /**
     * 处理并返回信息
     */
    protected inline fun messageResult(block: () -> String): ResultObj {
        return success(message = block())
    }

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
