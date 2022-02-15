package top.vuhe.admin.well.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.well.domina.ExportWell
import top.vuhe.admin.well.domina.WellInfo

/**
 * 井信息服务类
 *
 * @author vuhe
 */
interface IWellService: ICurdService<WellInfo> {
    /**
     * 导出数据列表
     */
    fun exportList(): List<ExportWell>
}
