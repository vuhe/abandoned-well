package top.vuhe.admin.well.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.ICodeService

/**
 * 地质分区代码管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/code")
class CodeController(
    private val codeService: ICodeService
) : BaseController() {

    /**
     * 用于管理地质代码的页面
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/well/code/main','well:code:main')")
    fun main() = ModelAndView("well/code/main")

    /**
     * 用于添加地质代码的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/well/code/add','well:code:add')")
    fun add() = ModelAndView("well/code/add")

    /**
     * 用于修改地质代码的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/well/code/edit','well:code:edit')")
    fun edit() = ModelAndView("well/code/edit")

    /* -------------------------------------------------------------------------- */

}
