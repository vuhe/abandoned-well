package top.vuhe.admin.system.service

import top.vuhe.admin.system.domain.SysMenu

/**
 * 菜单服务接口
 *
 * @author vuhe
 */
interface ISysMenuService {
    /**
     * 获取用户菜单数据
     */
    fun getUserMenu(userId: String): List<SysMenu>

    /**
     * 递归获取菜单tree
     */
    fun toUserMenu(sysMenus: List<SysMenu>, parentId: String): List<SysMenu>
}
