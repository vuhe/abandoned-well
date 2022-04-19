package top.vuhe.admin.system.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.TreeService
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.repository.LinkRolePower
import top.vuhe.admin.system.repository.SysPowerRepository

/**
 * 权限服务实现类
 *
 * @author vuhe
 */
@Service
class SysPowerService(
    private val linkRolePower: LinkRolePower,
    override val repository: SysPowerRepository
) : TreeService<SysPower>() {

    override fun list(param: PageParam): List<SysPower> {
        return super.list(param).sortedBy { it.sort }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(ids: List<String>): Boolean {
        // 删除关联表信息
        linkRolePower.deleteByPower(ids)
        return super.remove(ids)
    }
}
