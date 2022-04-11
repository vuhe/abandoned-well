package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.exception.businessRequire
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.service.ISysPowerService

/**
 * 权限控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "系统权限")
@RequestMapping(API_SYSTEM_PREFIX + "power")
class SysPowerController(
    private val sysPowerService: ISysPowerService
) : BaseController() {

    /**
     * 获取权限列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:main')")
    fun main() = ModelAndView("system/power/main")

    /**
     * 获取权限新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:add')")
    fun add() = ModelAndView("system/power/add")

    /**
     * 获取权限修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:edit')")
    fun edit(powerId: String) = ModelAndView("system/power/edit").apply {
        addObject("sysPower", sysPowerService.getOneById(powerId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 获取权限列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:data')")
    fun data(sysPower: SysPower) = treeTable { sysPowerService.list(sysPower) }

    /**
     * 保存权限信息
     */
    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:add')")
    fun save(@RequestBody sysPower: SysPower) = boolResult {
        businessRequire(sysPower.parentId.isNotBlank()) { "请选择上级菜单" }
        sysPowerService.add(sysPower)
    }

    /**
     * 修改权限信息
     */
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:edit')")
    fun update(@RequestBody sysPower: SysPower) = boolResult {
        businessRequire(sysPower.parentId.isNotBlank()) { "请选择上级菜单" }
        sysPowerService.modify(sysPower)
    }

    /**
     * 根据 id 进行删除
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:power:remove')")
    fun remove(@PathVariable id: String) = boolResult {
        businessRequire(sysPowerService.getByParentId(id).isEmpty()) { "请先删除下级权限" }
        sysPowerService.remove(id)
    }

    /**
     * 获取父级权限选择数据
     */
    @GetMapping("selectParent")
    fun selectParent(sysPower: SysPower) = dataTree {
        sysPowerService.list(sysPower)
    }

    /**
     * 根据 Id 开启用户
     */
    @PutMapping("enable")
    fun enable(@RequestBody sysPower: SysPower) = boolResult {
        sysPower.enable = true
        sysPowerService.modify(sysPower)
    }

    /**
     * 根据 Id 禁用用户
     */
    @PutMapping("disable")
    fun disable(@RequestBody sysPower: SysPower) = boolResult {
        sysPower.enable = false
        sysPowerService.modify(sysPower)
    }
}
