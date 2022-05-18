package top.vuhe.domain.well

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.annotation.Logging
import top.vuhe.logging.BusinessType
import top.vuhe.network.ContentType
import top.vuhe.network.setContent
import top.vuhe.network.setFilename
import top.vuhe.office.OfficeExport
import top.vuhe.web.controller.BaseController
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

        response.setContent(ContentType.FILE_XLS)
        response.setFilename("${date}信息汇总.xls")
        OfficeExport.excel(list, "doc/excel.xls").writeTo(response.outputStream)
    }

    /** 用于汇总导出 word */
    @GetMapping("word")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:summary:export')")
    @Logging("井导出", describe = "导出井 word 信息", type = BusinessType.EXPORT)
    fun word(response: HttpServletResponse) {
        val list = infoService.exportList()

        response.setContent(ContentType.FILE_DOCX)
        response.setFilename("${date}详情汇总.docx")
        OfficeExport.word(list, "doc/doc.docx").writeTo(response.outputStream)
    }

    private val date: String
        get() {
            val time = LocalDate.now()
            return "${time.year}年${time.monthValue}月${time.dayOfMonth}日"
        }

}
