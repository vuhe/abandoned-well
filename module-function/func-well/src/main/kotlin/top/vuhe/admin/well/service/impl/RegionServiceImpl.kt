package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.mapper.RegionMapper
import top.vuhe.admin.well.service.IRegionService

/**
 * 井区域服务类接口实现
 *
 * @author vuhe
 */
@Service
class RegionServiceImpl(regionMapper: RegionMapper) :
    CurdService<WellRegion>(regionMapper), IRegionService {
    private val emptyParam = WellRegion()
    override fun getAllRegion(): List<WellRegion> = list(emptyParam)
}
