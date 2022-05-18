package top.vuhe.domain.power

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.database.service.TreeService
import top.vuhe.mapper.LinkRolePower
import top.vuhe.web.request.PageParam

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
