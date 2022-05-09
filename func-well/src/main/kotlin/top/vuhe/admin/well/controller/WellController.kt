package top.vuhe.admin.well.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.ktorm.entity.Entity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.annotation.Logging
import top.vuhe.admin.api.logging.BusinessType
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.domina.WellInfo
import top.vuhe.admin.well.domina.WellStatus
import top.vuhe.admin.well.param.WellParam
import top.vuhe.admin.well.service.RegionService
import top.vuhe.admin.well.service.WellService
import javax.validation.Valid

/**
 * 井信息管理
 *
 * @author vuhe
 */
@RestController
@Tag(name = "井信息")
@RequestMapping("/well/info")
class WellController(
    private val infoService: WellService,
    private val regionService: RegionService,
) : BaseController() {

    /**
     * 用于管理的所有数据
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:main')")
    fun main() = ModelAndView("well/info/main")

    /**
     * 用于查看单个数据
     */
    @GetMapping("detail")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:detail')")
    fun detail(id: String) = ModelAndView("well/info/detail").apply {
        val well = infoService.getOneById(id)
        addObject("well", well)
        addObject("region", regionService.getOneById(well?.regionId ?: ""))
    }

    /**
     * 用于添加井信息的页面
     */
    @GetMapping("add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:add')")
    fun add() = ModelAndView("well/info/add").apply {
        addObject("regions", regionService.list())
    }

    /**
     * 用于审批的页面
     */
    @GetMapping("approve")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:approve')")
    fun approve(id: String) = ModelAndView("well/info/approve").apply {
        addObject("well", infoService.getOneById(id))
    }

    /**
     * 用于修改井信息的页面
     */
    @GetMapping("edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:edit')")
    fun edit(id: String): ModelAndView {
        val well = infoService.getOneById(id)
        // 通过审核的是动态更新
        return if (well?.status == WellStatus.Approved) {
            ModelAndView("well/info/report").apply {
                addObject("well", infoService.getOneById(id))
            }
            // 未通过审核的是全部更改
        } else ModelAndView("well/info/edit").apply {
            addObject("regions", regionService.list())
            addObject("well", infoService.getOneById(id))
        }
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 分页查询 井信息
     */
    @GetMapping("page")
    @Operation(summary = "分页查询井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:page')")
    fun page(param: WellParam) = buildPage {
        infoService.page(param)
    }

    /**
     * 添加 井信息
     */
    @PostMapping("save")
    @Operation(summary = "添加井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:add')")
    @Logging("井上报", describe = "添加井信息", type = BusinessType.ADD)
    fun save(@RequestBody @Valid info: WellInfo) = boolResult {
        info.status = WellStatus.Approved
        infoService.add(info)
    }

    /**
     * 修改 井信息
     */
    @PutMapping("update")
    @Operation(summary = "修改井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:edit')")
    @Logging("井修改", describe = "修改井信息", type = BusinessType.EDIT)
    fun update(@RequestBody @Valid info: WellInfo) = boolResult {
        infoService.modify(info)
    }

    /**
     * 动态更新 井信息
     */
    @PutMapping("report")
    @Operation(summary = "动态更新井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:edit')")
    @Logging("井更新", describe = "动态更新井信息", type = BusinessType.EDIT)
    fun report(@RequestBody info: WellInfo) = boolResult {
        infoService.modify(info)
    }

    /**
     * 通过审核 井信息
     */
    @PutMapping("approved/{id}")
    @Operation(summary = "通过审核井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:edit')")
    @Logging("井审核", describe = "通过审核井信息", type = BusinessType.EDIT)
    fun pass(@PathVariable("id") id: String) = boolResult {
        val info = Entity.create<WellInfo>().apply {
            this.id = id
            status = WellStatus.Approved
        }
        infoService.modify(info)
    }

    /**
     * 打回修改 井信息
     */
    @PutMapping("not_approved/{id}")
    @Operation(summary = "打回修改井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:edit')")
    @Logging("井审核", describe = "审核打回井信息", type = BusinessType.EDIT)
    fun notPass(@PathVariable("id") id: String) = boolResult {
        val info = Entity.create<WellInfo>().apply {
            this.id = id
            status = WellStatus.NotAccepted
        }
        infoService.modify(info)
    }

    /**
     * 删除 井信息
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "删除井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:remove')")
    @Logging("井删除", describe = "删除井信息", type = BusinessType.REMOVE)
    fun remove(@PathVariable("id") id: String) = boolResult {
        infoService.remove(id)
    }

    /**
     * 批量删除 井信息
     */
    @DeleteMapping("/batchRemove")
    @Operation(summary = "批量删除井信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','well:info:remove')")
    @Logging("井删除", describe = "删除井信息", type = BusinessType.REMOVE)
    fun batchRemove(ids: String) = boolResult {
        infoService.remove(ids.split(","))
    }
}
