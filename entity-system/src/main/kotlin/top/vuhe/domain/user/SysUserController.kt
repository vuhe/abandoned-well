package top.vuhe.domain.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.annotation.RepeatSubmit
import top.vuhe.domain.log.SysLogService
import top.vuhe.domain.role.SysRoleService
import top.vuhe.exception.businessRequire
import top.vuhe.security.securityContext
import top.vuhe.web.controller.BaseController

/**
 * 用户控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping("/system/user")
class SysUserController(
    private val sysUserService: SysUserService,
    private val sysRoleService: SysRoleService,
    private val sysLogService: SysLogService
) : BaseController() {
    private val currUserId by securityContext()

    /** 获取用户列表视图 */
    @GetMapping("main")
    @Operation(summary = "获取用户列表视图")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:main')")
    fun main() = ModelAndView("system/user/main")

    /** 用户新增视图 */
    @GetMapping("add")
    @Operation(summary = "获取用户新增视图")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:add')")
    fun add() = ModelAndView("system/user/add").apply {
        addObject("sysRoles", sysRoleService.list())
    }

    /** 用户修改视图 */
    @GetMapping("edit")
    @Operation(summary = "获取用户修改视图")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:edit')")
    fun edit(userId: String) = ModelAndView("system/user/edit").apply {
        addObject("sysRoles", sysUserService.getUserRole(userId))
        addObject("sysUser", sysUserService.getOneById(userId))
    }

    /** 用户密码修改视图 */
    @Suppress("SpellCheckingInspection")
    @GetMapping("editpasswordadmin")
    @Operation(summary = "获取管理员修改用户密码视图")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:editPasswordAdmin')")
    fun editPasswordAdminView(userId: String) =
        ModelAndView("system/user/editPasswordAdmin").apply {
            addObject("userId", userId)
        }

    /** 用户密码修改视图 */
    @GetMapping("editPassword")
    @Operation(summary = "普通用户修改密码")
    fun editPasswordView() = ModelAndView("system/user/password")

    /** 个人资料 */
    @GetMapping("center")
    @Operation(summary = "个人资料")
    fun center() = ModelAndView("system/user/center").apply {
        val userInfo = sysUserService.getOneById(currUserId)
        val logs = sysLogService.getTopLoginLog(currUserId)
        addObject("userInfo", userInfo)
        addObject("logs", logs)
    }

    /* -------------------------------------------------------------------------- */

    /** 获取用户列表数据 */
    @GetMapping("data")
    @Operation(summary = "获取用户列表数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:data')")
    fun data(param: SysUserParam) = buildPage {
        sysUserService.page(param)
    }

    /** 用户新增接口 */
    @RepeatSubmit
    @PostMapping("save")
    @Operation(summary = "保存用户数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:add')")
    fun save(@RequestBody sysUser: SysUser) = boolResult {
        sysUserService.add(sysUser)
        sysUserService.saveUserRole(sysUser.userId, sysUser.roles)
    }

    /** 管理员修改用户密码接口 */
    @PutMapping("editPasswordAdmin")
    @Operation(summary = "管理员修改用户密码")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:editPasswordAdmin')")
    fun editPasswordAdmin(@RequestBody editPassword: EditPassword) = boolResult {
        sysUserService.modifyPassword(
            editPassword.userId, editPassword.newPassword
        )
    }

    /** 用户密码修改接口 */
    @PutMapping("editPassword")
    fun editPassword(@RequestBody editPassword: EditPassword) = boolResult {
        val oldPassword: String = editPassword.oldPassword
        val newPassword: String = editPassword.newPassword

        businessRequire(editPassword.isNotEmpty()) { "输入不能为空" }
        businessRequire(editPassword.confirmOld()) { "两次密码输入不一致" }

        val sysUser = sysUserService.getOneById(currUserId)
        requireNotNull(sysUser) { "用户信息错误，请刷新后重试" }

        businessRequire(
            BCryptPasswordEncoder().matches(oldPassword, sysUser.password)
        ) { "旧密码输入错误" }

        sysUserService.modifyPassword(sysUser.userId, newPassword)
    }

    /** 用户修改接口 */
    @PutMapping("update")
    @Operation(summary = "修改用户数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:edit')")
    fun update(@RequestBody sysUser: SysUser) = boolResult {
        businessRequire(sysUser.password.isEmpty()) { "修改用户信息禁止修改密码" }
        if (sysUser.roles.isNotEmpty()) {
            sysUserService.saveUserRole(sysUser.userId, sysUser.roles)
        }
        sysUserService.modify(sysUser)
    }

    /** 用户批量删除接口 */
    @DeleteMapping("batchRemove/{ids}")
    @Operation(summary = "批量删除用户")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:remove')")
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysUserService.remove(ids.split(","))
    }

    /** 用户删除接口 */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "删除用户数据")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','sys:user:remove')")
    fun remove(@PathVariable id: String) = boolResult {
        sysUserService.remove(id)
    }

    /** 根据 userId 开启用户 */
    @PutMapping("enable")
    @Operation(summary = "开启用户登录")
    fun enable(@RequestBody sysUser: SysUser) = boolResult {
        sysUser.enable = true
        sysUserService.modify(sysUser)
    }

    /** 根据 userId 禁用用户 */
    @PutMapping("disable")
    @Operation(summary = "禁用用户登录")
    fun disable(@RequestBody sysUser: SysUser) = boolResult {
        sysUser.enable = false
        sysUserService.modify(sysUser)
    }

    /** 用户修改接口 */
    @PutMapping("updateInfo")
    @Operation(summary = "修改用户数据")
    fun updateInfo(@RequestBody sysUser: SysUser) = boolResult {
        sysUserService.modify(sysUser)
    }

    class EditPassword {
        var userId: String = ""

        /** 旧密码 */
        var oldPassword: String = ""

        /** 新密码 */
        var newPassword: String = ""

        /** 确认密码 */
        var confirmPassword: String = ""

        fun confirmOld(): Boolean = oldPassword == confirmPassword
        fun isNotEmpty(): Boolean {
            return oldPassword.isNotEmpty()
                    && newPassword.isNotEmpty()
                    && confirmPassword.isNotEmpty()
        }
    }
}
