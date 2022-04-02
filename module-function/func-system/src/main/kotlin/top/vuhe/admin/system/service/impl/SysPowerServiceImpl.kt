package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.mapper.LinkRolePower
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.service.ISysPowerService

/**
 * 权限服务实现类
 *
 * @author vuhe
 */
@Service
class SysPowerServiceImpl : CurdService<SysPower>(SysPowerMapper), ISysPowerService {
    private val linkRolePower = LinkRolePower
    private val sysPowerMapper = SysPowerMapper

    override fun list(param: SysPower): List<SysPower> {
        return super.list(param).sortedBy { it.sort }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 删除关联表信息
        linkRolePower.deleteByPower(ids)
        return super.batchRemove(ids)
    }

    override fun getByParentId(parentId: String): List<SysPower> {
        return sysPowerMapper.selectByParentId(parentId)
    }
}
