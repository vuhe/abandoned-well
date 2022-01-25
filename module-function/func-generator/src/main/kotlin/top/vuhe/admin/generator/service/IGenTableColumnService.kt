package top.vuhe.admin.generator.service

import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.spring.database.service.ICurdService

/**
 * 业务表字段服务层
 *
 * @author vuhe
 */
interface IGenTableColumnService: ICurdService<GenTableColumn> {
    /**
     * 通过 table id 查询列
     */
    fun listByTableId(tableId: String): List<GenTableColumn>
}
