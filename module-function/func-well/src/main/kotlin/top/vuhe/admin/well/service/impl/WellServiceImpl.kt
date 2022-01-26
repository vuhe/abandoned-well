package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.mapper.WellMapper
import top.vuhe.admin.well.service.IWellService

/**
 * 井信息服务类接口实现
 *
 * @author vuhe
 */
@Service
class WellServiceImpl(
    private val infoMapper: WellMapper
) : CurdService<WellInfo>(infoMapper), IWellService {
}
