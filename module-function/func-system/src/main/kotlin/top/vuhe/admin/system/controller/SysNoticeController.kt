package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysNotice
import top.vuhe.admin.system.service.ISysNoticeService
import top.vuhe.admin.system.service.ISysUserService

/**
 * 消息控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "消息公告")
@RequestMapping("/system/notice")
class SysNoticeController(
    private val sysNoticeService: ISysNoticeService,
    private val sysUserService: ISysUserService
) : BaseController() {

    @GetMapping("/main")
    @PreAuthorize("hasPermission('/system/notice/main','system:notice:main')")
    fun main() = ModelAndView("system/notice/main")

    /**
     * 新增notice
     */
    @GetMapping("/add")
    @PreAuthorize("hasPermission('/system/notice/add','system:notice:add')")
    fun add() = ModelAndView("system/notice/add").apply {
        addObject("users", sysUserService.list())
    }

    /**
     * 修改notice
     */
    @GetMapping("/edit")
    @PreAuthorize("hasPermission('/system/notice/edit','system:notice:edit')")
    fun edit(id: String) = ModelAndView("system/notice/edit").apply {
        addObject("sysNotice", sysNoticeService.getOneById(id))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 查询notice列表
     */
    @GetMapping("/data")
    @PreAuthorize("hasPermission('/system/notice/data','system:notice:data')")
    fun list(@ModelAttribute sysNotice: SysNotice, pageDomain: PageDomain) = pageTable {
        sysNoticeService.page(sysNotice, pageDomain)
    }

    /**
     * 查询消息
     */
    @GetMapping("notice")
    fun notice(): List<Map<String, Any>> {
        val publicParam = SysNotice().apply { type = "public" }
        val noticeParam = SysNotice().apply { type = "notice" }
        val privateParam = SysNotice().apply {
            accept = currUserId
            type = "private"
        }
        val publicArray = mapOf(
            "id" to "1", "title" to "公告",
            "children" to sysNoticeService.list(publicParam)
        )
        val privateArray = mapOf(
            "id" to "2", "title" to "私信",
            "children" to sysNoticeService.list(privateParam)
        )
        val noticeArray = mapOf(
            "id" to "3", "title" to "通知",
            "children" to sysNoticeService.list(noticeParam)
        )
        return listOf(publicArray, privateArray, noticeArray)
    }

    /**
     * 新增保存notice
     */
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/system/notice/add','system:notice:add')")
    fun save(@RequestBody sysNotice: SysNotice) = boolResult {
        sysNoticeService.add(sysNotice)
    }

    /**
     * 修改保存notice
     */
    @PutMapping("/update")
    @PreAuthorize("hasPermission('/system/notice/edit','system:notice:edit')")
    fun update(@RequestBody sysNotice: SysNotice) = boolResult {
        sysNoticeService.modify(sysNotice)
    }

    /**
     * 删除notice
     */
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/system/notice/remove','system:notice:remove')")
    fun batchRemove(ids: String) = boolResult {
        sysNoticeService.batchRemove(ids.split(","))
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/system/notice/remove','system:notice:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        sysNoticeService.remove(id)
    }
}
