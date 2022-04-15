package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.system.domain.SysMenu
import top.vuhe.admin.system.repository.LinkRolePower
import top.vuhe.admin.system.repository.LinkUserRole
import top.vuhe.admin.system.repository.SysPowerRepository
import top.vuhe.admin.system.repository.SysUserRepository
import top.vuhe.admin.system.service.ISysMenuService

/**
 * 菜单服务接口实现
 *
 * @author vuhe
 */
@Service
class SysMenuServiceImpl(
    private val sysUserRepository: SysUserRepository,
    private val linkUserRole: LinkUserRole,
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) : ISysMenuService {

    override fun getUserMenu(userId: String): List<SysMenu> {
        val user = sysUserRepository.selectById(userId)

        val list = if (user?.admin == true) {
            sysPowerRepository.selectListByAdmin()
        } else {
            // 全部角色信息
            val roleIds = linkUserRole.selectRoleIdByUserId(userId)

            // 全部菜单信息
            val powerIds = roleIds.map { linkRolePower.selectPowerIdByRoleId(it) }.flatten()

            sysPowerRepository.selectListByIds(powerIds)
        }

        // 转换为 menu
        return list.map {
            SysMenu(
                id = it.powerId, parentId = it.parentId, title = it.powerName,
                username = userId, type = it.powerType, icon = it.icon,
                openType = it.openType, href = it.powerUrl, sort = it.sort
            )
        }
    }

    override fun toUserMenu(sysMenus: List<SysMenu>, parentId: String): List<SysMenu> {
        return sysMenus.mapNotNull { menu ->
            if (parentId == menu.parentId) {
                menu.children = toUserMenu(sysMenus, menu.id)
                menu
            } else null
        }.sortedBy { it.sort }
    }
}
