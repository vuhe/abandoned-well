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
class LogServiceImpl(logMapper: LogMapper) :
    CurdService<WellLog>(logMapper), ILogService {
    override fun record(wellId: String, logType: String) {
        val log = WellLog().apply {
            this.wellId = wellId
            this.logType = logType
            username = "" // TODO modify username
        }
//        add(log)
    }
}
