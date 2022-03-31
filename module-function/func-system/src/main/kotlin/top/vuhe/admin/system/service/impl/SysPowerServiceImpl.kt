package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.service.ISysPowerService

/**
 * 权限服务实现类
 *
 * @author vuhe
 */
@Service
class SysPowerServiceImpl : CurdService<SysPower>(SysPowerMapper), ISysPowerService {
    override fun getByParentId(parentId: String): List<SysPower> {
        return SysPowerMapper.selectByParentId(parentId)
    }
}
