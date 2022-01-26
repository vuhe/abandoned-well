package top.vuhe.admin.well.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.well.service.IRegionService

/**
 * 井区域管理
 *
 * @author vuhe
 */
@RestController
@RequestMapping("/well/region")
class RegionController(
    private val regionService: IRegionService
) : BaseController() {
}
