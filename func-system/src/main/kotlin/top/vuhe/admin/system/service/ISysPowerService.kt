package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysPower

/**
 * 权限服务接口类
 *
 * @author vuhe
 */
interface ISysPowerService : ICurdService<SysPower> {
    /**
     * 权限是否有子节点，无返回 true
     */
    fun hasNoChildNodes(id: String): Boolean
}
