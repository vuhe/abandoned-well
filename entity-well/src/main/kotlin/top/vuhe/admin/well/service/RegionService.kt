package top.vuhe.admin.well.service

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.repository.RegionRepository

/**
 * 井区域服务类接口实现
 *
 * @author vuhe
 */
@Service
class RegionService(override val repository: RegionRepository) : CurdService<WellRegion>()
