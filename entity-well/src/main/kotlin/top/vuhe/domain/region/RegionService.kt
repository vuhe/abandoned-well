package top.vuhe.domain.region

import org.springframework.stereotype.Service
import top.vuhe.database.service.CurdService

/**
 * 井区域服务类接口实现
 *
 * @author vuhe
 */
@Service
class RegionService(override val repository: RegionRepository) : CurdService<WellRegion>()
