package top.vuhe.admin.well.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.IWellService

/**
 * 井信息汇总管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/summary")
class SummaryController(
    private val infoService: IWellService
) : BaseController() {

    /**
     * 用于汇总导出的页面
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/well/summary/main','well:summary:main')")
    fun main() = ModelAndView("well/summary/main")

    /* -------------------------------------------------------------------------- */


}
