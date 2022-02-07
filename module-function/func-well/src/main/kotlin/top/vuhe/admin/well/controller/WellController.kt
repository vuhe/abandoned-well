package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.domina.WellStatus
import top.vuhe.admin.well.service.IWellService

/**
 * 井信息管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "井信息")
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
     * 用于查看单个数据
     */
    @GetMapping("detail")
    @PreAuthorize("hasPermission('/well/info/detail','well:info:detail')")
    fun detail(id: String) = ModelAndView("well/info/detail").apply {
        addObject("well", infoService.getOneById(id))
    }

    /**
     * 用于添加井信息的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/well/info/add','well:info:add')")
    fun add() = ModelAndView("well/info/add")

    /**
     * 用于审批的页面
     */
    @GetMapping("approve")
    @PreAuthorize("hasPermission('/well/info/approve','well:info:approve')")
    fun approve(id: String) = ModelAndView("well/info/approve").apply {
        addObject("well", infoService.getOneById(id))
    }

    /**
     * 用于修改井信息的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/well/info/edit','well:info:edit')")
    fun edit(id: String): ModelAndView {
        val well = infoService.getOneById(id)
        // 通过审核的是动态更新
        return if (well?.status == WellStatus.Approved) {
            ModelAndView("well/info/report").apply {
                addObject("well", infoService.getOneById(id))
            }
            // 未通过审核的是全部更改
        } else ModelAndView("well/info/edit").apply {
            addObject("well", infoService.getOneById(id))
        }
    }

    /* -------------------------------------------------------------------------- */


    /**
     * 分页查询 井信息
     */
    @GetMapping("page")
    @PreAuthorize("hasPermission('/well/info/page','well:info:page')")
    fun page(info: WellInfo, pageDomain: PageDomain) = pageTable {
        infoService.page(info, pageDomain)
    }

//    /**
//     * 添加 井信息
//     */
//    @PostMapping("save")
//    @PreAuthorize("hasPermission('/well/info/add','well:info:add')")
//    fun save(@RequestBody info: WellInfo) = boolResult {
//        infoService.add(info)
//    }
//
//    /**
//     * 修改 井信息
//     */
//    @PutMapping("update")
//    @PreAuthorize("hasPermission('/well/info/edit','well:info:edit')")
//    fun update(@RequestBody info: WellInfo) = boolResult {
//        infoService.modify(info)
//    }

    /**
     * 删除 井信息
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/well/info/remove','well:info:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        infoService.remove(id)
    }

    /**
     * 批量删除 井信息
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/well/info/remove','well:info:remove')")
    fun batchRemove(ids: String) = boolResult {
        infoService.batchRemove(ids.split(","))
    }
}
