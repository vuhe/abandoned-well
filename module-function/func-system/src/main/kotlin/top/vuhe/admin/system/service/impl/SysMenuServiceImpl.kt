package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.BaseService
import top.vuhe.admin.spring.property.SecurityProperty
import top.vuhe.admin.system.domain.SysMenu
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.mapper.SysRoleMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import top.vuhe.admin.system.service.ISysMenuService

/**
 * 菜单服务接口实现
 *
 * @author vuhe
 */
@Service
class SysMenuServiceImpl(
    private val securityProperty: SecurityProperty,
    private val sysUserMapper: SysUserMapper,
    private val sysRoleMapper: SysRoleMapper,
    private val sysPowerMapper: SysPowerMapper
) : BaseService(), ISysMenuService {
    override fun getUserMenu(userId: String): List<SysMenu> {
        val user = sysUserMapper.selectById(userId)

        val list = if (securityProperty.superAuthOpen && user?.admin == true) {
            sysPowerMapper.selectListByAdmin()
        } else {
            // 全部角色信息
            val roleIds = sysUserMapper.selectRoleIdByUserId(userId)

            // 全部菜单信息
            val powerIds = roleIds.map { sysRoleMapper.selectPowerIdByRoleId(it) }.flatten()

            sysPowerMapper.selectListByIds(powerIds)
        }

        // 转换为 menu
        return list.map {
            SysMenu(
                id = it.powerId, parentId = it.parentId, title = it.powerName,
                username = userId, type = it.powerType, icon = it.icon,
                openType = it.openType, href = it.powerUrl
            )
        }
    }

    override fun toUserMenu(sysMenus: List<SysMenu>, parentId: String): List<SysMenu> {
        return sysMenus.mapNotNull { menu ->
            if (parentId == menu.parentId) {
                menu.children = toUserMenu(sysMenus, menu.id)
                menu
            } else null
        }
    }
}
