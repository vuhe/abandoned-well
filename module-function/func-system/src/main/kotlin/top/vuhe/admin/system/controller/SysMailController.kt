package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.system.domain.SysMail
import top.vuhe.admin.system.service.ISysMailService

/**
 * 邮箱控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "邮箱管理")
@RequestMapping(API_SYSTEM_PREFIX + "mail")
class SysMailController(
    private val sysMailService: ISysMailService
) : BaseController() {

    /**
     * 邮件管理页面
     */
    @GetMapping("/main")
    @Operation(summary = "邮件管理页面")
    @PreAuthorize("hasPermission('/system/mail/main','sys:mail:main')")
    fun main() = if (sysMailService.enable)
        ModelAndView("system/mail/main")
    else ModelAndView("error/not_available")

    /**
     * 邮件发送页面
     */
    @GetMapping("/add")
    @Operation(summary = "邮件发送页面")
    @PreAuthorize("hasPermission('/system/mail/add','sys:mail:add')")
    fun add() = ModelAndView("system/mail/add")

    /* -------------------------------------------------------------------------- */

    /**
     * 邮件列表数据
     */
    @GetMapping("/data")
    @Operation(summary = "邮件列表数据")
    @PreAuthorize("hasPermission('/system/mail/data','sys:mail:data')")
    fun data(sysMail: SysMail, pageDomain: PageDomain) = pageTable {
        sysMailService.page(sysMail, pageDomain)
    }

    /**
     * 邮件保存和发送
     */
    @PostMapping("/save")
    @Operation(summary = "邮件保存和发送")
    @PreAuthorize("hasPermission('/system/mail/save','sys:mail:save')")
    fun save(@RequestBody sysMail: SysMail): ResultObj<*> {
        return try {
            boolResult { sysMailService.save(sysMail) }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultObj.Fail<Nothing>(message = "请检查邮箱配置")
        }
    }

    /**
     * 删除邮件
     */
    @DeleteMapping("/remove/{mailId}")
    @Operation(summary = "删除邮件")
    @PreAuthorize("hasPermission('/system/mail/remove','sys:mail:remove')")
    fun remove(@PathVariable mailId: String) = boolResult {
        sysMailService.remove(mailId)
    }

    /**
     * 批量删除邮件
     */
    @DeleteMapping("/batchRemove/{ids}")
    @Operation(summary = "批量删除邮件")
    @PreAuthorize("hasPermission('/system/mail/remove','sys:mail:remove')")
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysMailService.batchRemove(ids.split(","))
    }
}
