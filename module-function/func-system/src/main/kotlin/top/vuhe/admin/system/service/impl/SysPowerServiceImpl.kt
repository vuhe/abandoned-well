package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.service.ISysPowerService

/**
 * 权限服务实现类
 *
 * @author vuhe
 */
@Service
class SysPowerServiceImpl(
    private val sysPowerMapper: SysPowerMapper
) : CurdService<SysPower>(sysPowerMapper), ISysPowerService {

    /**
     * 此接口不支持
     */
    @Deprecated("UnsupportedOperation")
    override fun page(param: SysPower, pageDomain: PageDomain): TablePage<SysPower> {
        throw UnsupportedOperationException()
    }

    override fun getByParentId(parentId: String): List<SysPower> {
        return sysPowerMapper.selectByParentId(parentId)
    }

}
