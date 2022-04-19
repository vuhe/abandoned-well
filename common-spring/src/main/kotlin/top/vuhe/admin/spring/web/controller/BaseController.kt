package top.vuhe.admin.spring.web.controller

import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.response.AjaxResult
import top.vuhe.admin.spring.web.response.LayuiTable
import top.vuhe.admin.spring.web.response.LayuiTree

/**
 * ### 统一响应 Controller
 *
 * @author vuhe
 */
abstract class BaseController {

    protected fun success(message: String = "操作成功") = AjaxResult.success(message)

    protected fun fail() = AjaxResult.fail()

    /**
     * bool 处理结果
     */
    protected inline fun boolResult(block: () -> Boolean): AjaxResult {
        return if (block()) success() else fail()
    }

    /**
     * 处理并返回信息
     */
    protected inline fun messageResult(block: () -> String): AjaxResult {
        return success(message = block())
    }

    /**
     * 返回 Tree 数据
     */
    protected inline fun buildTree(block: () -> List<*>) = LayuiTree(block())

    /**
     * 返回数据表格数据 分页
     */
    protected inline fun buildPage(block: () -> TablePage<*>): LayuiTable {
        return block().let { LayuiTable(it.list, it.count.toLong()) }
    }

    /**
     * 返回数据表格数据
     */
    protected inline fun buildTable(block: () -> List<*>) = LayuiTable(block())

}
