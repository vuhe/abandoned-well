package top.vuhe.domain.user

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.exception.businessRequire
import top.vuhe.security.SecuritySessionManager
import top.vuhe.web.controller.BaseController
import java.time.Duration
import java.time.LocalDateTime

/**
 * 在线用户控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "在线用户")
@RequestMapping("/system/online")
class SysOnlineController(
    private val sessionRegistry: SecuritySessionManager,
    private val sysUserService: SysUserService
) : BaseController() {

    /**
     * 获取在线用户列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:online:main')")
    fun main() = ModelAndView("system/user/online")

    /* -------------------------------------------------------------------------- */

    /**
     * 在线用户列表
     */
    @GetMapping("data")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:online:data')")
    fun data(username: String?) = buildTable {
        sessionRegistry.allPrincipals.mapNotNull {
            val user = sysUserService.getOneById(it as String)
            if (user == null) null
            else SysOnlineUser().apply {
                userId = user.userId
                this.username = user.username
                realName = user.realName
                lastTime = user.lastTime
                onlineTime = "${Duration.between(user.lastTime, LocalDateTime.now()).toMinutes()}分钟"
            }
        }
    }

    /**
     * 踢出用户（下线）
     */
    @DeleteMapping("/remove/{onlineId}")
    fun remove(@PathVariable onlineId: String) = messageResult {
        sysUserService.getOneById(onlineId)?.let {
            // 不允许操作admin用户下线
            businessRequire(!it.admin) { "不允许操作超级管理员[admin]下线" }
            sessionRegistry.deleteSessionByUserId(it.userId)
            "用户[${it.username}]已下线"
        } ?: "用户信息错误，请刷新重试"
    }

    class SysOnlineUser {
        lateinit var userId: String
        lateinit var username: String
        lateinit var realName: String
        var lastTime: LocalDateTime? = null
        lateinit var onlineTime: String
    }

}
