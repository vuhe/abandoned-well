package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.exception.businessRequire
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.param.SysDeptParam
import top.vuhe.admin.system.service.ISysDeptService

/**
 * 部门管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "组织部门")
@RequestMapping(API_SYSTEM_PREFIX + "dept")
class SysDeptController(
    private val sysDeptService: ISysDeptService
) : BaseController() {

    /**
     * 获取部门列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:main')")
    fun main() = ModelAndView("system/dept/main")

    /**
     * 获取部门新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:add')")
    fun add() = ModelAndView("system/dept/add")

    /**
     * 获取部门修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun edit(deptId: String) = ModelAndView("system/dept/edit").apply {
        addObject("sysDept", sysDeptService.getOneById(deptId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 获取部门列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:data')")
    fun data(param: SysDeptParam) = dataTable { sysDeptService.list(param) }

    /**
     * 获取部门树状数据结构
     */
    @GetMapping("tree")
    fun tree() = dataTree { sysDeptService.list() }

    /**
     * 保存部门信息
     */
    @PostMapping("save")
    @Operation(summary = "保存部门数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:add')")
    fun save(@RequestBody sysDept: SysDept) = boolResult {
        sysDeptService.add(sysDept)
    }

    /**
     * 修改部门信息
     */
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun update(@RequestBody sysDept: SysDept) = boolResult {
        sysDeptService.modify(sysDept)
    }

    /**
     * 部门删除接口
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:remove')")
    fun remove(@PathVariable id: String) = boolResult {
        businessRequire(sysDeptService.getByParentId(id).isEmpty()) { "请先删除下级部门" }
        sysDeptService.remove(id)
    }

    /**
     * 部门批量删除接口
     */
    @DeleteMapping("batchRemove/{ids}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:remove')")
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysDeptService.batchRemove(ids.split(","))
    }

    /**
     * 根据 Id 启用部门
     */
    @PutMapping("enable")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun enable(@RequestBody sysDept: SysDept) = boolResult {
        sysDept.enable = true
        sysDeptService.modify(sysDept)
    }

    /**
     * 根据 Id 禁用部门
     */
    @PutMapping("disable")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun disable(@RequestBody sysDept: SysDept) = boolResult {
        sysDept.enable = false
        sysDeptService.modify(sysDept)
    }
}
