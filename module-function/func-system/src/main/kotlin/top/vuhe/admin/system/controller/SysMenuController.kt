package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.system.domain.SysMenu
import top.vuhe.admin.system.service.ISysMenuService

/**
 * 菜单控制器
 *
 * @author vuhe
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping(API_SYSTEM_PREFIX + "menu/")
class SysMenuController(private val sysMenuService: ISysMenuService) {

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
