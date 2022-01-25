package top.vuhe.admin.generator.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.FileCopyUtils
import top.vuhe.admin.api.constant.SLASH
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.generator.handler.GenInfoConvert.init
import top.vuhe.admin.generator.handler.TemplateRenderer.makeFullPathFileName
import top.vuhe.admin.generator.handler.TemplateRenderer.render
import top.vuhe.admin.generator.mapper.GenTableColumnMapper
import top.vuhe.admin.generator.mapper.GenTableMapper
import top.vuhe.admin.generator.service.IGenTableService
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.spring.web.request.PageDomain
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 业务表服务实现
 *
 * @author vuhe
 */
@Service
class GenTableServiceImpl(
    private val objectMapper: ObjectMapper,
    private val genTableMapper: GenTableMapper,
    private val genTableColumnMapper: GenTableColumnMapper
) : CurdService<GenTable>(genTableMapper), IGenTableService {

    override fun getOneById(id: String): GenTable? {
        return super.getOneById(id)?.apply {
            setTableFromOptions()
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modify(entity: GenTable): Boolean {
        val options = objectMapper.writeValueAsString(entity.params)
        entity.options = options
        val result = super.modify(entity)

        if (result) {
            genTableColumnMapper.batchUpdate(entity.columns)
        }
        return result
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        genTableColumnMapper.batchDeleteByTableId(ids)
        return super.batchRemove(ids)
    }

    override fun pageDatabaseTable(genTable: GenTable, pageDomain: PageDomain): TablePage<GenTable> {
        val list = genTableMapper.selectTable(genTable)
        return TablePage(list, pageDomain)
    }

    override fun listDatabaseTableByName(tableNames: List<String>): List<GenTable> {
        return genTableMapper.selectTableByName(tableNames)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun importGenTable(tableList: List<GenTable>): Boolean {
        try {
            // 导入表
            tableList.forEach { it.init() }
            val row = genTableMapper.batchInsert(tableList)

            // 导入每个表所有列
            if (row > 0) {
                tableList.forEach { table ->
                    val tableColumns: List<GenTableColumn> =
                        genTableColumnMapper.selectTableColumnByName(table.tableName)
                    tableColumns.forEach { it.init(table) }
                    genTableColumnMapper.batchInsert(tableColumns)
                }
            }
            return true
        } catch (e: Exception) {
            throw BusinessException("导入失败：" + e.message)
        }
    }

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    override fun previewCode(tableId: String): Map<String, String> {
        val dataMap: MutableMap<String, String> = LinkedHashMap()
        val table: GenTable = genTableMapper.selectById(tableId)
            ?: return emptyMap()
        table.setSubTable()
        table.setPkColumn()

        render(table) { name, template ->
            dataMap[name] = template
        }

        return dataMap
    }

    override fun downloadCode(tableNames: List<String>): ByteArray {
        return ByteArrayOutputStream().use {
            val zip = ZipOutputStream(it)
            for (tableName in tableNames) {
                generatorCode(tableName, zip)
            }
            it.toByteArray()
        }
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     * @return 数据
     */
    override fun generatorCode(tableName: String) {
        val table: GenTable = genTableMapper.selectByName(tableName)
            ?: throw BusinessException("表名称有误，请检查表名称")
        table.setSubTable()
        table.setPkColumn()

        render(table) { name, template ->
            if ("sql.vm" in name) {
                println("生成的sql:--------\n$template")
            } else {
                try {
                    val path = getGenPath(table, name)
                    FileCopyUtils.copy(template.toByteArray(), File(path))
                } catch (e: IOException) {
                    throw BusinessException("渲染模板失败，表名：" + table.tableName)
                }
            }
        }
    }

    /**
     * 查询表信息并生成代码
     *
     * @param tableName 表名
     * @param zip       压缩文件流
     */
    private fun generatorCode(tableName: String, zip: ZipOutputStream) {
        val table: GenTable = genTableMapper.selectByName(tableName)
            ?: return
        table.setSubTable()
        table.setPkColumn()

        render(table) { name, template ->
            try {
                zip.putNextEntry(ZipEntry(makeFullPathFileName(table, name)))
                zip.write(template.toByteArray())
                zip.flush()
                zip.closeEntry()
            } catch (e: IOException) {
                log.error("渲染模板失败，表名：" + table.tableName, e)
            }
        }
    }

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    override fun validateEdit(genTable: GenTable) {
        when (genTable.tplCategory) {
            "tree" -> {
                genTable.treeCode.ifEmpty { throw BusinessException("树编码字段不能为空") }
                genTable.treeParentCode.ifEmpty { throw BusinessException("树父编码字段不能为空") }
            }
            "sub" -> {
                genTable.subTableName.ifEmpty { throw BusinessException("关联子表的表名不能为空") }
                genTable.subTableFkName.ifEmpty { throw BusinessException("子表关联的外键名不能为空") }
            }
        }
    }

    /**
     * 设置主键列信息
     */
    private fun GenTable.setPkColumn() {
        // 主表主键设置
        pkColumn = columns.find { it.isPk == true }
        if (pkColumn == null) pkColumn = columns[0]

        // 子表（如果有）主键设置
        if (tplCategory == "sub") {
            val subTable = subTable!!
            subTable.pkColumn = subTable.columns.find { it.isPk == true }
            if (subTable.pkColumn == null) subTable.pkColumn = subTable.columns[0]
        }
    }

    /**
     * 设置主子表信息
     */
    private fun GenTable.setSubTable() {
        val subTableName = subTableName
        if (subTableName.isNotEmpty()) {
            subTable = genTableMapper.selectByName(subTableName)
        }
    }

    /**
     * 设置代码生成其他选项值
     */
    private fun GenTable.setTableFromOptions() {
        val paramsMap: Map<String, String> = try {
            objectMapper.readValue(options)
        } catch (_: Exception) {
            emptyMap()
        }

        this.treeCode = paramsMap["treeCode"] ?: ""
        this.treeParentCode = paramsMap["treeParentCode"] ?: ""
        this.treeName = paramsMap["treeName"] ?: ""
        this.parentMenuId = paramsMap["parentMenuId"] ?: ""
        this.parentMenuName = paramsMap["parentMenuName"] ?: ""
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * 获取代码生成地址
         *
         * @param table    业务表信息
         * @param template 模板文件路径
         * @return 生成地址
         */
        private fun getGenPath(table: GenTable, template: String): String {
            val genPath = table.genPath
            return if (genPath == SLASH) {
                System.getProperty("user.dir") + File.separator + "src" +
                        File.separator + makeFullPathFileName(table, template)
            } else genPath + File.separator + makeFullPathFileName(table, template)
        }
    }
}
