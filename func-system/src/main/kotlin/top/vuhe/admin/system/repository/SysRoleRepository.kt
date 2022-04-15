package top.vuhe.admin.system.repository

import org.ktorm.dsl.like
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.param.SysRoleParam
import top.vuhe.admin.system.table.SysRoleTable

/**
 * 系统角色接口
 *
 * @author vuhe
 */
@Repository
class SysRoleRepository : CurdRepository<SysRoleTable, SysRole>(cacheable = true) {
    override val table get() = SysRoleTable
    override var SysRole.entityId: String by SysRole::roleId

    override fun find(params: PageParam) = buildList {
        params as SysRoleParam
        if (params.roleCode.isNotBlank()) add(table.roleCode like "%${params.roleCode}%")
        if (params.roleName.isNotBlank()) add(table.roleName like "%${params.roleName}%")
    }
}
