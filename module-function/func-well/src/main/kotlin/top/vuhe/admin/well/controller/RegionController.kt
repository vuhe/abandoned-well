package top.vuhe.admin.well.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.IRegionService

/**
 * 井区域管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/region")
class RegionController(
    private val regionService: IRegionService
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
    fun add() = ModelAndView("well/region/add")

    /**
     * 用于修改区域的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/well/region/edit','well:region:edit')")
    fun edit() = ModelAndView("well/region/edit")

    /* -------------------------------------------------------------------------- */

}
