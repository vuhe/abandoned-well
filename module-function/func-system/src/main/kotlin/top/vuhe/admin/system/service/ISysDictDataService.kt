package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysDictData

/**
 * 字典值服务类
 *
 * @author vuhe
 */
interface ISysDictDataService : ICurdService<SysDictData> {

    /**
     * 根据字典code获取可用的字典列表数据
     */
    fun selectByCode(typeCode: String): List<SysDictData>

    /**
     * 查询表字典通过查询指定table的 text code key 获取字典值
     *
     * @param table 表名
     * @param text  label
     * @param code  value
     * @return
     */
    fun queryTableDictItemsByCode(table: String, text: String, code: String): List<SysDictData>

    /**
     * 查询表字典 通过查询指定table的 text code 获取字典（指定查询条件）
     *
     * @param table 表名
     * @param text  label
     * @param code  value
     * @param filterSql 条件
     */
    fun queryTableDictItemsByCodeAndFilter(
        table: String, text: String, code: String, filterSql: String
    ): List<SysDictData>

    /**
     * 查询表字典 通过查询指定table的 text code key 获取字典值，包含value
     *
     * @param table    表名
     * @param text     label
     * @param code     value
     * @param keyArray values
     * @return
     */
    fun queryTableDictByKeys(
        table: String, text: String, code: String, keyArray: List<String>
    ): List<SysDictData>
}
