package top.vuhe.domain.role

import org.springframework.stereotype.Repository
import top.vuhe.database.repository.CurdRepository

/**
 * 系统角色接口
 *
 * @author vuhe
 */
@Repository
class SysRoleRepository : CurdRepository<SysRoleTable, SysRole>() {
    override val cacheName: String? get() = table.tableName
    override val table get() = SysRoleTable
    override var SysRole.entityId: String by SysRole::roleId
}
