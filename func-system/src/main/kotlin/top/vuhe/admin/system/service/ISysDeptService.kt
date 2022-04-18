package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysDept

/**
 * 部门服务接口类
 *
 * @author vuhe
 */
interface ISysDeptService : ICurdService<SysDept> {
    /**
     * 部门是否有子节点，无返回 true
     */
    fun hasNoChildNodes(id: String): Boolean
}
