package top.vuhe.admin.system.mapper

import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysRole

/**
 * 系统角色接口
 *
 * @author vuhe
 */
object SysRoleMapper : CurdMapper<SysRole>("sys_role") {
    init {
        enableCache()
    }
}
