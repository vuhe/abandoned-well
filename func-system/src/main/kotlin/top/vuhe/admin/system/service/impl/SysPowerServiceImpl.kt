package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.repository.LinkRolePower
import top.vuhe.admin.system.repository.SysPowerRepository
import top.vuhe.admin.system.service.ISysPowerService

/**
 * 权限服务实现类
 *
 * @author vuhe
 */
@Service
class SysPowerServiceImpl(
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) : CurdService<SysPower>(sysPowerRepository), ISysPowerService {

    override fun list(param: PageParam): List<SysPower> {
        return super.list(param).sortedBy { it.sort }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 删除关联表信息
        linkRolePower.deleteByPower(ids)
        return super.batchRemove(ids)
    }

    override fun hasNoChildNodes(id: String): Boolean {
        return sysPowerRepository.countChildren(id) == 0
    }
}
