package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.repository.RegionRepository
import top.vuhe.admin.well.service.IRegionService

/**
 * 井区域服务类接口实现
 *
 * @author vuhe
 */
@Service
class RegionServiceImpl(
    regionRepository: RegionRepository
) : CurdService<WellRegion>(regionRepository), IRegionService
