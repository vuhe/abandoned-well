package top.vuhe.admin.well.repository

import org.ktorm.dsl.like
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.param.WellParam
import top.vuhe.admin.well.table.WellTable

/**
 * 井信息接口
 *
 * @author vuhe
 */
@Repository
class WellRepository : CurdRepository<WellTable, WellInfo>() {
    override val table get() = WellTable
    override var WellInfo.entityId: String by WellInfo::id

    override fun find(params: PageParam) = buildList {
        params as WellParam
        if (params.name.isNotBlank()) add(table.name like "%${params.name}%")
    }
}
