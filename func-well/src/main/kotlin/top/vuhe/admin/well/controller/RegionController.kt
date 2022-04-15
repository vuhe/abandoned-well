package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.param.RegionParam
import top.vuhe.admin.well.service.ICodeService
import top.vuhe.admin.well.service.IRegionService
import javax.validation.Valid

/**
 * 井区域管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "井区域")
@RequestMapping("/well/region")
class RegionController(
    private val regionService: IRegionService,
    private val codeService: ICodeService
) : BaseController() {

    /**
     * 用于管理区域的页面
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:main')")
    fun main() = ModelAndView("well/region/main")

    /**
     * 用于添加区域的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:add')")
    fun add() = ModelAndView("well/region/add").apply {
        addObject("codes", codeService.listWithChecked(""))
    }

    /**
     * 用于修改区域的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:edit')")
    fun edit(id: String) = ModelAndView("well/region/edit").apply {
        val region = regionService.getOneById(id)
        addObject("region", region)
        addObject("codes", codeService.listWithChecked(region?.regionCodeId ?: ""))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 分页查询 井区域
     */
    @GetMapping("page")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:page')")
    fun page(param: RegionParam) = pageTable {
        regionService.page(param)
    }

    /**
     * 添加 井区域
     */
    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:add')")
    fun save(@RequestBody @Valid region: WellRegion) = boolResult {
        regionService.add(region)
    }

    /**
     * 修改 井区域
     */
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:edit')")
    fun update(@RequestBody @Valid region: WellRegion) = boolResult {
        regionService.modify(region)
    }

    /**
     * 删除 井区域
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        regionService.remove(id)
    }

    /**
     * 批量删除 井区域
     */
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:region:remove')")
    fun batchRemove(ids: String) = boolResult {
        regionService.batchRemove(ids.split(","))
    }
}
