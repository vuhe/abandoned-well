package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.session.SessionRegistry
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.spring.web.session.HttpSessionCenter
import top.vuhe.admin.system.service.ISysUserService
import java.time.Duration
import java.time.LocalDateTime

/**
 * 在线用户控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "在线用户")
@RequestMapping(API_SYSTEM_PREFIX + "online")
class SysOnlineController(
    private val sessionRegistry: SessionRegistry,
    private val sysUserService: ISysUserService
) : BaseController() {

    /**
     * 获取在线用户列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/online/main','sys:online:main')")
    fun main() = ModelAndView("system/user/online")

    /* -------------------------------------------------------------------------- */

    /**
     * 在线用户列表
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/online/data','sys:online:data')")
    fun data(username: String?) = dataTable {
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
    @ResponseBody
    fun remove(@PathVariable onlineId: String): ResultObj<*> {
        // 从sessionRegistry中获取所有的用户信息
        val userId = sessionRegistry.allPrincipals.find {
            it as String == onlineId
        } as String?
        sysUserService.getOneById(userId ?: "")?.let {
            // 不允许操作admin用户下线
            if (it.admin == true) {
                return ResultObj.Fail<Nothing>(message = "不允许操作超级管理员[admin]下线")
            }
            for (sessionInformation in sessionRegistry.getAllSessions(it, false)) {
                sessionInformation.expireNow()
                // 从sessionRegistry中清除session信息
                sessionRegistry.removeSessionInformation(sessionInformation.sessionId)
                // 销毁session
                HttpSessionCenter[sessionInformation.sessionId]?.invalidate()
            }
            return ResultObj.Success<Nothing>(message = "用户[${it.username}]已下线")
        }
        return ResultObj.Fail<Nothing>()
    }

    class SysOnlineUser {
        lateinit var userId: String
        lateinit var username: String
        lateinit var realName: String
        var lastTime: LocalDateTime? = null
        lateinit var onlineTime: String
    }

}
