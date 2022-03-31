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
     * 根据 parentId 查询权限
     */
    fun getByParentId(parentId: String): List<SysPower>
}
