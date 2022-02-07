package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.well.domina.WellRegion
import top.vuhe.admin.well.service.ICodeService
import top.vuhe.admin.well.service.IRegionService

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
    @PreAuthorize("hasPermission('/well/region/main','well:region:main')")
    fun main() = ModelAndView("well/region/main")

    /**
     * 用于添加区域的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/well/region/add','well:region:add')")
    fun add() = ModelAndView("well/region/add").apply {
        addObject("codes", codeService.listWithChecked(""))
    }

    /**
     * 用于修改区域的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/well/region/edit','well:region:edit')")
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
    @PreAuthorize("hasPermission('/well/region/page','well:region:page')")
    fun page(region: WellRegion, pageDomain: PageDomain) = pageTable {
        regionService.page(region, pageDomain)
    }

    /**
     * 添加 井区域
     */
    @PostMapping("save")
    @PreAuthorize("hasPermission('/well/region/add','well:region:add')")
    fun save(@RequestBody region: WellRegion) = boolResult {
        regionService.add(region)
    }

    /**
     * 修改 井区域
     */
    @PutMapping("update")
    @PreAuthorize("hasPermission('/well/region/edit','well:region:edit')")
    fun update(@RequestBody region: WellRegion) = boolResult {
        regionService.modify(region)
    }

    /**
     * 删除 井区域
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/well/region/remove','well:region:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        regionService.remove(id)
    }

    /**
     * 批量删除 井区域
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/well/region/remove','well:region:remove')")
    fun batchRemove(ids: String) = boolResult {
        regionService.batchRemove(ids.split(","))
    }
}
