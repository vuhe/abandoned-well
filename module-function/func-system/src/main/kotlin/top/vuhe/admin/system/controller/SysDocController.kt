package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.web.controller.BaseController

/**
 * 接口文档控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "接口文档")
@RequestMapping(API_SYSTEM_PREFIX + "doc")
class SysDocController : BaseController() {

    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:doc:main')")
    fun main() = ModelAndView("system/doc/main")
}
