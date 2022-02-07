package top.vuhe.admin.well.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.well.domina.RegionCode

/**
 * 地质分区代码服务类
 *
 * @author vuhe
 */
interface ICodeService: ICurdService<RegionCode> {
    /**
     * 查询全部代码，并对选中代码标记
     */
    fun listWithChecked(codeId: String): List<RegionCode>
}
