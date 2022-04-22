package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.vuhe.admin.spring.security.securityContext
import top.vuhe.admin.system.domain.SysMenu
import top.vuhe.admin.system.service.SysMenuService

/**
 * 菜单控制器
 *
 * @author vuhe
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu/")
class SysMenuController(private val sysMenuService: SysMenuService) {
    private val currUserId by securityContext()

    /**
     * 根据 username 获取菜单数据
     */
    @Operation(summary = "获取用户菜单数据")
    @GetMapping("/data")
    fun getUserMenu(): List<SysMenu> {
        val menus = sysMenuService.getUserMenu(currUserId)
        return sysMenuService.toUserMenu(menus, "0")
    }
}
