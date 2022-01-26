package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.well.domina.WellLog
import top.vuhe.admin.well.mapper.LogMapper
import top.vuhe.admin.well.service.ILogService

/**
 * 井修改日志服务类接口实现
 *
 * @author vuhe
 */
@Service
class LogServiceImpl(
    private val logMapper: LogMapper
) : CurdService<WellLog>(logMapper), ILogService {
}
