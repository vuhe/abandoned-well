package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.param.SysDeptParam
import top.vuhe.admin.system.table.SysDeptTable

/**
 * 部门接口
 *
 * @author vuhe
 */
@Repository
class SysDeptRepository : CurdRepository<SysDeptTable, SysDept>() {

    override val table get() = SysDeptTable

    override var SysDept.entityId: String by SysDept::deptId

    override fun find(params: PageParam) = buildList {
        params as SysDeptParam
        if (params.deptName.isNotBlank()) add(table.deptName like "%${params.deptName}%")
    }

    /**
     * 根据 parentId 查询部门
     */
    fun selectListByParentId(id: String): List<SysDept> {
        return entities.filter { it.parentId eq id }.toList()
    }
}
