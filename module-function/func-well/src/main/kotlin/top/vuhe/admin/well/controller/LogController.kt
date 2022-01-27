package top.vuhe.admin.well.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.well.domina.WellLog
import top.vuhe.admin.well.service.ILogService

/**
 * 井信息日志管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/log")
class LogController(
    private val logService: ILogService
) : BaseController() {
    private val emptyQuery = WellLog()

    /**
     * 用于管理查看日志的页面
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/well/log/main','well:log:main')")
    fun main() = ModelAndView("well/log/main")

    /**
     * 用于查看日志详情的页面
     */
    @GetMapping("detail")
    @PreAuthorize("hasPermission('/well/log/detail','well:log:detail')")
    fun detail() = ModelAndView("well/log/detail")

    /* -------------------------------------------------------------------------- */

    /**
     * 操作日志数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/log/data','sys:log:data')")
    fun data(pageDomain: PageDomain) = pageTable {
        logService.page(emptyQuery, pageDomain)
    }

}
