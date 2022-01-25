package top.vuhe.admin.generator.service

import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.web.request.PageDomain

/**
 * 业务表服务接口
 *
 * @author vuhe
 */
interface IGenTableService : ICurdService<GenTable> {

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    fun list() = list(GenTable())

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    fun pageDatabaseTable(genTable: GenTable, pageDomain: PageDomain): TablePage<GenTable>

    /**
     * 查询据库列表
     */
    fun listDatabaseTableByName(tableNames: List<String>): List<GenTable>

    /**
     * 导入表结构
     */
    fun importGenTable(tableList: List<GenTable>): Boolean

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    fun previewCode(tableId: String): Map<String, String>

    /**
     * 生成代码（下载方式）
     *
     * @param tableName 表名称
     * @return 数据
     */
    fun downloadCode(tableName: String): ByteArray =
        downloadCode(listOf(tableName))

    /**
     * 批量生成代码（下载方式）
     *
     * @param tableNames 表数组
     * @return 数据
     */
    fun downloadCode(tableNames: List<String>): ByteArray

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     * @return 数据
     */
    fun generatorCode(tableName: String)

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    fun validateEdit(genTable: GenTable)
}
