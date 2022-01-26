package top.vuhe.admin.well.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}
