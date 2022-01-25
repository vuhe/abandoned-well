package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysDictType
import top.vuhe.admin.system.service.ISysDictTypeService

/**
 * 数据字典控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "数据字典")
@RequestMapping(API_SYSTEM_PREFIX + "dictType")
class SysDictTypeController(
    private val sysDictTypeService: ISysDictTypeService
) : BaseController() {

    /**
     * 数据字典列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/dictType/main','sys:dictType:main')")
    fun main() = ModelAndView("system/dict/main")

    /**
     * 数据字典类型新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/system/dictType/add','sys:dictType:add')")
    fun add() = ModelAndView("system/dict/add")

    /**
     * 数据字典类型修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/system/dictType/edit','sys:dictType:edit')")
    fun edit(dictTypeId: String) = ModelAndView("system/dict/edit").apply {
        addObject("sysDictType", sysDictTypeService.getOneById(dictTypeId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 数据字典列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/dictType/data','sys:dictType:data')")
    fun data(sysDictType: SysDictType, pageDomain: PageDomain) = pageTable {
        sysDictTypeService.page(sysDictType, pageDomain)
    }

    @GetMapping("list")
    @PreAuthorize("hasPermission('/system/dictType/data','sys:dictType:data')")
    fun list(sysDictType: SysDictType) = dataTable {
        sysDictTypeService.list(sysDictType)
    }

    /**
     * 新增字典类型接口
     */
    @PostMapping("save")
    @PreAuthorize("hasPermission('/system/dictType/add','sys:dictType:add')")
    fun save(@RequestBody sysDictType: SysDictType) = boolResult {
        sysDictTypeService.add(sysDictType)
    }

    /**
     * 数据字典类型修改视图
     */
    @PutMapping("update")
    @PreAuthorize("hasPermission('/system/dictType/edit','sys:dictType:edit')")
    fun update(@RequestBody sysDictType: SysDictType) = boolResult {
        sysDictTypeService.modify(sysDictType)
    }

    /**
     * 数据字典删除
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/system/dictType/remove','sys:dictType:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        sysDictTypeService.remove(id)
    }

    /**
     * 根据 Id 启用字典
     */
    @PutMapping("enable")
    fun enable(@RequestBody sysDictType: SysDictType) = boolResult {
        sysDictType.enable = true
        sysDictTypeService.modify(sysDictType)
    }

    /**
     * 根据 Id 禁用字典
     */
    @PutMapping("disable")
    fun disable(@RequestBody sysDictType: SysDictType) = boolResult {
        sysDictType.enable = false
        sysDictTypeService.modify(sysDictType)
    }
}
