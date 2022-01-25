package top.vuhe.admin.generator.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.generator.mapper.GenTableColumnMapper
import top.vuhe.admin.generator.service.IGenTableColumnService
import top.vuhe.admin.spring.database.service.impl.CurdService

/**
 * 业务表字段服务实现
 *
 * @author vuhe
 */
@Service
class GenTableColumnServiceImpl(
    private val tableColumnMapper: GenTableColumnMapper
) : CurdService<GenTableColumn>(tableColumnMapper), IGenTableColumnService {

    override fun listByTableId(tableId: String): List<GenTableColumn> {
        return tableColumnMapper.selectByTableId(tableId)
    }
}
