package top.vuhe.domain.dept

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.exception.businessRequire
import top.vuhe.web.controller.BaseController

/**
 * 部门管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "组织部门")
@RequestMapping("/system/dept")
class SysDeptController(private val sysDeptService: SysDeptService) : BaseController() {

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

    @GetMapping("data")
    @Operation(summary = "获取部门列表数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:data')")
    fun deptData(param: SysDeptParam) = buildTable { sysDeptService.list(param) }

    @GetMapping("tree")
    @Operation(summary = "获取部门树状数据结构")
    fun deptTree() = buildTree { sysDeptService.list() }

    @PostMapping("save")
    @Operation(summary = "保存部门信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:add')")
    fun save(@RequestBody sysDept: SysDept) = boolResult {
        sysDeptService.add(sysDept)
    }

    @PutMapping("update")
    @Operation(summary = "修改部门信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun update(@RequestBody sysDept: SysDept) = boolResult {
        sysDeptService.modify(sysDept)
    }

    @DeleteMapping("remove/{id}")
    @Operation(summary = "部门删除接口")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:remove')")
    fun remove(@PathVariable id: String) = boolResult {
        businessRequire(sysDeptService.isLeafNode(id)) { "请先删除下级部门" }
        sysDeptService.remove(id)
    }

    @DeleteMapping("batchRemove/{ids}")
    @Operation(summary = "部门批量删除接口")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:remove')")
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysDeptService.remove(ids.split(","))
    }

    @PutMapping("enable")
    @Operation(summary = "根据 Id 启用部门")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun enable(@RequestBody sysDept: SysDept) = boolResult {
        sysDept.enable = true
        sysDeptService.modify(sysDept)
    }

    @PutMapping("disable")
    @Operation(summary = "根据 Id 禁用部门")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:dept:edit')")
    fun disable(@RequestBody sysDept: SysDept) = boolResult {
        sysDept.enable = false
        sysDeptService.modify(sysDept)
    }
}
