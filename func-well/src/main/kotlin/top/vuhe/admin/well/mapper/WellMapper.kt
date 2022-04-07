package top.vuhe.admin.well.mapper

import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.well.domina.WellInfo

/**
 * 井信息接口
 *
 * @author vuhe
 */
object WellMapper : CurdMapper<WellInfo>("well_info") {
    /**
     * 使用根据文档规则生成的 id 插入
     */
    override fun generateId(entity: WellInfo): String = entity.id
}
