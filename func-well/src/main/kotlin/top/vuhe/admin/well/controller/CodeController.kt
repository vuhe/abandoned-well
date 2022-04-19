package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.domina.RegionCode
import top.vuhe.admin.well.param.CodeParam
import top.vuhe.admin.well.service.CodeService

/**
 * 地质分区代码管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "地质分区代码")
@RequestMapping("/well/code")
class CodeController(
    private val codeService: CodeService
) : BaseController() {

    /**
     * 用于管理地质代码的页面
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:main')")
    fun main() = ModelAndView("well/code/main")

    /**
     * 用于添加地质代码的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:add')")
    fun add() = ModelAndView("well/code/add")

    /**
     * 用于修改地质代码的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:edit')")
    fun edit(id: String) = ModelAndView("well/code/edit").apply {
        addObject("code", codeService.getOneById(id))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 分页查询 地质代码
     */
    @GetMapping("page")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:page')")
    fun page(param: CodeParam) = buildPage {
        codeService.page(param)
    }

    /**
     * 添加 地质代码
     */
    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:add')")
    fun save(@RequestBody code: RegionCode) = boolResult {
        codeService.add(code)
    }

    /**
     * 修改 地质代码
     */
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:edit')")
    fun update(@RequestBody code: RegionCode) = boolResult {
        codeService.modify(code)
    }

    /**
     * 删除 地质代码
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        codeService.remove(id)
    }

    /**
     * 批量删除 地质代码
     */
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:code:remove')")
    fun batchRemove(ids: String) = boolResult {
        codeService.remove(ids.split(","))
    }
}
