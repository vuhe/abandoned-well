package top.vuhe.web.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import top.vuhe.monitor.Monitor

/**
 * 服务器控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "服务监控")
@RequestMapping("/system/monitor")
class MonitorController : BaseController() {
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:monitor:main')")
    fun main() = ModelAndView("system/monitor/main").apply {
        addObject("info", Monitor)
    }
}
