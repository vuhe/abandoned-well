package top.vuhe.admin.well.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.IWellService

/**
 * 井信息管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/info")
class WellController(
    private val infoService: IWellService
) : BaseController() {

    /**
     * 用于管理的所有数据
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/well/info/main','well:info:main')")
    fun main() = ModelAndView("well/info/main")

    /**
     * 用于审批的页面
     */
    @GetMapping("approve")
    @PreAuthorize("hasPermission('/well/info/approve','well:info:approve')")
    fun approve() = ModelAndView("well/info/approve")

    /**
     * 用于添加井信息的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/well/info/add','well:info:add')")
    fun add() = ModelAndView("well/info/add")

    /**
     * 用于修改井信息的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/well/info/edit','well:info:edit')")
    fun edit() :ModelAndView {
        // TODO("未通过审核的是全部更改")
        ModelAndView("well/info/edit")
        // TODO("通过审核的是动态更新")
        return ModelAndView("well/info/report")
    }

    /* -------------------------------------------------------------------------- */

}
