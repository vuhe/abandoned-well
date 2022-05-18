package top.vuhe.domain.power

import org.springframework.stereotype.Service
import top.vuhe.domain.user.SysUserRepository
import top.vuhe.mapper.LinkRolePower
import top.vuhe.mapper.LinkUserRole

/**
 * 菜单服务接口实现
 *
 * @author vuhe
 */
@Service
class SysMenuService(
    private val sysUserRepository: SysUserRepository,
    private val linkUserRole: LinkUserRole,
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) {

    /**
     * 获取用户菜单数据
     */
    fun getUserMenu(userId: String): List<SysMenu> {
        val user = sysUserRepository.selectById(userId)

        val list = if (user?.admin == true) {
            sysPowerRepository.selectList().asSequence()
        } else {
            // 全部角色信息
            val roleIds = linkUserRole.selectRoleIdByUserId(userId).asSequence()

            // 全部菜单信息
            val powerIds = roleIds.map { linkRolePower.selectPowerIdByRoleId(it) }
                .flatten().distinct()

            powerIds.mapNotNull { sysPowerRepository.selectById(it) }
        }

        // 转换为 menu
        return list.filter { it.enable }.map {
            SysMenu(
                id = it.powerId, parentId = it.parentId, title = it.powerName,
                username = userId, type = it.powerType, icon = it.icon,
                openType = it.openType, href = it.powerUrl, sort = it.sort
            )
        }.toList()
    }

    /**
     * 递归获取菜单tree
     */
    fun toUserMenu(sysMenus: List<SysMenu>, parentId: String): List<SysMenu> {
        return sysMenus.mapNotNull { menu ->
            if (parentId == menu.parentId) {
                menu.children = toUserMenu(sysMenus, menu.id)
                menu
            } else null
        }.sortedBy { it.sort }
    }
}
