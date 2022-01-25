package top.vuhe.admin.generator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_GENERATOR_PREFIX
import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.generator.service.IGenTableColumnService
import top.vuhe.admin.generator.service.IGenTableService
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.spring.web.response.ResultSelect
import javax.servlet.http.HttpServletResponse

/**
 * 代码生成控制器
 *
 * @author vuhe
 */
@Controller
@Tag(name = "代码生成")
@RequestMapping(API_GENERATOR_PREFIX)
class GenController(
    private val objectMapper: ObjectMapper,
    private val genTableService: IGenTableService,
    private val genTableColumnService: IGenTableColumnService
) : BaseController() {

    /**
     * 代码生成页面
     */
    @GetMapping("main")
    fun gen() = ModelAndView("generate/gen")

    /**
     * 导入表结构
     */
    @GetMapping("/importTable")
    fun importTable() = ModelAndView("generate/importTable")

    /**
     * 修改代码生成业务
     */
    @GetMapping("/edit")
    fun edit(tableId: String) = ModelAndView("generate/edit").apply {
        val table = genTableService.getOneById(tableId)
        val genTables: List<GenTable> = genTableService.list()
        val cxSelect = genTables.mapNotNull { genTable ->
            if (table?.tableName != genTable.tableName) {
                val cxTable = ResultSelect(genTable.tableName, genTable.tableName + '：' + genTable.tableComment)
                cxTable.s = genTable.columns.map {
                    ResultSelect(it.columnName, it.columnName + '：' + it.columnComment)
                }
                cxTable
            } else null
        }
        addObject("table", table)
        addObject("data", objectMapper.writeValueAsString(cxSelect))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 查询代码生成列表
     */
    @GetMapping("/list")
    @ResponseBody
    fun genList(genTable: GenTable, pageDomain: PageDomain) = pageTable {
        genTableService.page(genTable, pageDomain)
    }

    /**
     * 查询数据库列表
     */
    @GetMapping("/db/list")
    @ResponseBody
    fun dataList(genTable: GenTable, pageDomain: PageDomain) = pageTable {
        genTableService.pageDatabaseTable(genTable, pageDomain)
    }

    /**
     * 查询数据表字段列表
     */
    @GetMapping("/column/list")
    @ResponseBody
    fun columnList(genTableColumn: GenTableColumn) = dataTable {
        genTableColumnService.listByTableId(genTableColumn.tableId)
    }

    /**
     * 导入表结构（保存）
     */
    @PostMapping("/importTable")
    @ResponseBody
    fun importTableSave(tables: String) = boolResult("导入成功", "导入失败") {
        val tableNames = tables.split(",")
        val tableList = genTableService.listDatabaseTableByName(tableNames)
        genTableService.importGenTable(tableList)
    }

    /**
     * 修改保存代码生成业务
     */
    @PostMapping("/edit")
    @ResponseBody
    fun editSave(@Validated genTable: GenTable) = boolResult("保存成功", "保存失败") {
        genTableService.validateEdit(genTable)
        genTableService.modify(genTable)
    }

    /**
     * 根据 编号 删除代码生成配置
     */
    @PostMapping("/remove")
    @ResponseBody
    fun remove(ids: String) = boolResult {
        genTableService.batchRemove(ids.split(","))
    }

    /**
     * 预览代码
     *
     * @param tableId 表格编号
     */
    @GetMapping("/preview/{tableId}")
    @ResponseBody
    fun preview(@PathVariable("tableId") tableId: String): ResultObj<*> {
        val dataMap = genTableService.previewCode(tableId)
        return ResultObj.Success(data = dataMap)
    }

    /**
     * 下载代码
     */
    @GetMapping("/download/{tableName}")
    fun download(response: HttpServletResponse, @PathVariable("tableName") tableName: String) {
        val data = genTableService.downloadCode(tableName)
        writeData(response, data)
    }

    /**
     * 生成代码
     */
    @GetMapping("/genCode/{tableName}")
    @ResponseBody
    fun genCode(@PathVariable("tableName") tableName: String): ResultObj<*> {
        genTableService.generatorCode(tableName)
        return ResultObj.Success<Nothing>()
    }

    /**
     * 批量生成代码
     */
    @GetMapping("/batchGenCode")
    @ResponseBody
    fun batchGenCode(response: HttpServletResponse, tables: String) {
        val tableNames = tables.split(",")
        val data: ByteArray = genTableService.downloadCode(tableNames)
        writeData(response, data)
    }

    private fun writeData(response: HttpServletResponse, data: ByteArray) {
        response.reset()
        response.setHeader("Content-Disposition", "attachment; filename=\"admin.zip\"")
        response.addHeader("Content-Length", "" + data.size)
        response.contentType = "application/octet-stream; charset=UTF-8"
        FileCopyUtils.copy(data, response.outputStream)
    }
}
