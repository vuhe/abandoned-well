package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.annotation.Logging
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.api.office.OfficeHandler
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.WellService
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse

/**
 * 井信息汇总管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "井信息汇总")
@RequestMapping("/well/summary")
class SummaryController(
    private val infoService: WellService,
) : BaseController() {

    /** 用于汇总导出的页面 */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:summary:main')")
    fun main() = ModelAndView("well/summary/main")

    /* -------------------------------------------------------------------------- */

    /** 用于汇总导出 excel */
    @GetMapping("excel")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:summary:export')")
    @Logging("井导出", describe = "导出井 excel 信息", type = BusinessType.EXPORT)
    fun excel(response: HttpServletResponse) {
        val list = infoService.exportList()
        OfficeHandler.exportExcel(list, "doc/excel.xls", "${data}信息汇总", response)
    }

    /** 用于汇总导出 word */
    @GetMapping("word")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:summary:export')")
    @Logging("井导出", describe = "导出井 word 信息", type = BusinessType.EXPORT)
    fun word(response: HttpServletResponse) {
        val list = infoService.exportList()
        OfficeHandler.exportWord(list, "doc/doc.docx", "${data}详情汇总", response)
    }

    private val data: String
        get() {
            val time = LocalDate.now()
            return "${time.year}年${time.monthValue}月${time.dayOfMonth}日"
        }

}
