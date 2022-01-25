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
     * 根据 parentId 查询部门数据
     */
    fun getByParentId(parentId: String): List<SysDept>
}