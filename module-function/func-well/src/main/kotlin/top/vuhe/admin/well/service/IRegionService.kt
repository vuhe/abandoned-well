package top.vuhe.admin.well.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.well.domina.WellRegion

/**
 * 井区域服务类
 *
 * @author vuhe
 */
interface IRegionService: ICurdService<WellRegion> {
    /**
     * 获取全部区域信息
     */
    fun getAllRegion(): List<WellRegion>
}
