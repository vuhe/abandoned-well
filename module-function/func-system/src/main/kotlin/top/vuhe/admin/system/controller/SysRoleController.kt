package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.service.ISysRoleService

/**
 * 角色控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "系统角色")
@RequestMapping(API_SYSTEM_PREFIX + "role")
class SysRoleController(
    private val sysRoleService: ISysRoleService
) : BaseController() {

    /**
     * 获取角色列表视图
     */
    @GetMapping("main")
    @Operation(summary = "获取角色列表视图")
    @PreAuthorize("hasPermission('/system/role/main','sys:role:main')")
    fun main() = ModelAndView("system/role/main")

    /**
     * 获取角色新增视图
     */
    @GetMapping("add")
    @Operation(summary = "获取角色新增视图")
    @PreAuthorize("hasPermission('/system/role/add','sys:role:add')")
    fun add() = ModelAndView("system/role/add")

    /**
     * 获取角色授权视图
     */
    @GetMapping("power")
    @Operation(summary = "获取分配角色权限视图")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    fun power(roleId: String) = ModelAndView("system/role/power").apply {
        addObject("roleId", roleId)
    }

    /**
     * 获取角色修改视图
     */
    @GetMapping("edit")
    @Operation(summary = "获取角色修改视图")
    @PreAuthorize("hasPermission('/system/role/edit','sys:role:edit')")
    fun edit(roleId: String) = ModelAndView("system/role/edit").apply {
        addObject("sysRole", sysRoleService.getOneById(roleId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 获取角色列表数据
     */
    @GetMapping("data")
    @Operation(summary = "获取角色列表数据")
    @PreAuthorize("hasPermission('/system/role/data','sys:role:data')")
    fun data(pageDomain: PageDomain, param: SysRole) = pageTable {
        sysRoleService.page(param, pageDomain)
    }

    /**
     * 保存角色信息
     */
    @PostMapping("save")
    @Operation(summary = "保存角色数据")
    @PreAuthorize("hasPermission('/system/role/add','sys:role:add')")
    fun save(@RequestBody sysRole: SysRole) = boolResult {
        sysRoleService.add(sysRole)
    }

    /**
     * 修改角色信息
     */
    @PutMapping("update")
    @Operation(summary = "修改角色数据")
    @PreAuthorize("hasPermission('/system/role/edit','sys:role:edit')")
    fun update(@RequestBody sysRole: SysRole) = boolResult {
        sysRoleService.modify(sysRole)
    }

    /**
     * 保存角色权限
     */
    @PutMapping("saveRolePower")
    @Operation(summary = "保存角色权限数据")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    fun saveRolePower(roleId: String, powerIds: String) = boolResult {
        sysRoleService.saveRolePower(roleId, powerIds.split(","))
    }

    /**
     * 获取角色权限
     */
    @GetMapping("getRolePower")
    @Operation(summary = "获取角色权限数据")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    fun getRolePower(roleId: String) = dataTree {
        sysRoleService.getRolePower(roleId)
    }

    /**
     * 用户删除接口
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "删除角色数据")
    @PreAuthorize("hasPermission('/system/role/remove','sys:role:remove')")
    fun remove(@PathVariable id: String) = boolResult {
        sysRoleService.remove(id)
    }

    /**
     * 用户批量删除接口
     */
    @DeleteMapping("batchRemove/{ids}")
    @Operation(summary = "批量删除角色数据")
    @PreAuthorize("hasPermission('/system/role/remove','sys:role:remove')")
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysRoleService.batchRemove(ids.split(","))
    }

    /**
     * 根据 Id 启用角色
     */
    @PutMapping("enable")
    @Operation(summary = "启用角色")
    fun enable(@RequestBody sysRole: SysRole) = boolResult {
        sysRole.enable = true
        sysRoleService.modify(sysRole)
    }

    /**
     * 根据 Id 禁用角色
     */
    @PutMapping("disable")
    @Operation(summary = "禁用角色")
    fun disable(@RequestBody sysRole: SysRole) = boolResult {
        sysRole.enable = false
        sysRoleService.modify(sysRole)
    }
}
