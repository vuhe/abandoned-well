package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.enums.BusinessType
import top.vuhe.admin.api.logging.Logging
import top.vuhe.admin.spring.security.principal.LoginUserInfo.currUserId
import top.vuhe.admin.spring.web.annotation.RepeatSubmit
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.system.domain.SysUser
import top.vuhe.admin.system.service.ISysLogService
import top.vuhe.admin.system.service.ISysRoleService
import top.vuhe.admin.system.service.ISysUserService

/**
 * 用户控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping(API_SYSTEM_PREFIX + "user")
class SysUserController @Autowired constructor(
    private val sysUserService: ISysUserService,
    private val sysRoleService: ISysRoleService,
    private val sysLogService: ISysLogService
) : BaseController() {
    /**
     * 获取用户列表视图
     */
    @GetMapping("main")
    @Operation(summary = "获取用户列表视图")
    @PreAuthorize("hasPermission('/system/user/main','sys:user:main')")
    fun main() = ModelAndView("system/user/main")

    /**
     * 用户新增视图
     */
    @GetMapping("add")
    @Operation(summary = "获取用户新增视图")
    @PreAuthorize("hasPermission('/system/user/add','sys:user:add')")
    fun add() = ModelAndView("system/user/add").apply {
        addObject("sysRoles", sysRoleService.list())
    }

    /**
     * 用户修改视图
     */
    @GetMapping("edit")
    @Operation(summary = "获取用户修改视图")
    @PreAuthorize("hasPermission('/system/user/edit','sys:user:edit')")
    fun edit(userId: String) = ModelAndView("system/user/edit").apply {
        addObject("sysRoles", sysUserService.getUserRole(userId))
        addObject("sysUser", sysUserService.getOneById(userId))
    }

    /**
     * 用户密码修改视图
     */
    @GetMapping("editpasswordadmin")
    @Operation(summary = "获取管理员修改用户密码视图")
    @PreAuthorize("hasPermission('/system/user/editPasswordAdmin','sys:user:editPasswordAdmin')")
    fun editPasswordAdminView(userId: String) =
        ModelAndView("system/user/editPasswordAdmin").apply {
            addObject("userId", userId)
        }

    /**
     * 用户密码修改视图
     */
    @GetMapping("editPassword")
    fun editPasswordView() = ModelAndView("system/user/password")

    /**
     * 个人资料
     */
    @GetMapping("center")
    @Operation(summary = "个人资料")
    fun center() = ModelAndView("system/user/center").apply {
        val userInfo = sysUserService.getOneById(currUserId)
        val logs = sysLogService.getTopLoginLog(currUserId)
        addObject("userInfo", userInfo)
        addObject("logs", logs)
    }

    /**
     * 更换头像
     */
    @GetMapping("profile/{id}")
    fun profile(@PathVariable("id") userId: String) =
        ModelAndView("system/user/profile").apply {
            addObject("userId", userId)
        }

    /* -------------------------------------------------------------------------- */

    /**
     * 获取用户列表数据
     */
    @GetMapping("data")
    @Operation(summary = "获取用户列表数据")
    @PreAuthorize("hasPermission('/system/user/data','sys:user:data')")
    @Logging("查询用户", describe = "查询用户", type = BusinessType.QUERY)
    fun data(pageDomain: PageDomain, param: SysUser) = pageTable {
        sysUserService.page(param, pageDomain)
    }

    /**
     * 用户新增接口
     */
    @RepeatSubmit
    @PostMapping("save")
    @Operation(summary = "保存用户数据")
    @PreAuthorize("hasPermission('/system/user/add','sys:user:add')")
    @Logging("新增用户", describe = "新增用户", type = BusinessType.ADD)
    fun save(@RequestBody sysUser: SysUser) = boolResult {
        sysUserService.saveUserRole(sysUser.userId, sysUser.roleIds.split(","))
        sysUserService.add(sysUser)
    }

    /**
     * 管理员修改用户密码接口
     */
    @PutMapping("editPasswordAdmin")
    @Operation(summary = "管理员修改用户密码")
    @PreAuthorize("hasPermission('/system/user/editPasswordAdmin','sys:user:editPasswordAdmin')")
    fun editPasswordAdmin(@RequestBody editPassword: EditPassword) =
        boolResult("修改成功", "修改失败") {
            sysUserService.modifyPassword(
                editPassword.userId, editPassword.newPassword
            )
        }

    /**
     * 用户密码修改接口
     */
    @PutMapping("editPassword")
    fun editPassword(@RequestBody editPassword: EditPassword): ResultObj<*> {
        val oldPassword: String = editPassword.oldPassword
        val newPassword: String = editPassword.newPassword
        val confirmPassword: String = editPassword.confirmPassword
        val sysUser = sysUserService.getOneById(currUserId)
        if (confirmPassword.isBlank()
            || newPassword.isBlank()
            || oldPassword.isBlank()
        ) {
            return ResultObj.Fail<Nothing>(message = "输入不能为空")
        }
        if (!BCryptPasswordEncoder().matches(oldPassword, sysUser?.password ?: "")) {
            return ResultObj.Fail<Nothing>(message = "密码验证失败")
        }
        if (newPassword != confirmPassword) {
            return ResultObj.Fail<Nothing>(message = "两次密码输入不一致")
        }
        return boolResult("修改成功", "修改失败") {
            sysUserService.modifyPassword(sysUser?.userId ?: "", newPassword)
        }
    }

    /**
     * 用户修改接口
     */
    @PutMapping("update")
    @Operation(summary = "修改用户数据")
    @PreAuthorize("hasPermission('/system/user/edit','sys:user:edit')")
    @Logging("修改用户", describe = "修改用户", type = BusinessType.EDIT)
    fun update(@RequestBody sysUser: SysUser) = boolResult {
        sysUserService.saveUserRole(sysUser.userId, sysUser.roleIds.split(","))
        sysUserService.modify(sysUser)
    }

    /**
     * 头像修改接口
     */
    @PutMapping("updateAvatar")
    @Operation(summary = "修改用户头像")
    @Logging("修改头像", describe = "修改头像", type = BusinessType.EDIT)
    fun updateAvatar(@RequestBody sysUser: SysUser) = boolResult {
        val userId: String = currUserId
        sysUser.userId = userId
        sysUserService.modify(sysUser)
    }

    /**
     * 用户批量删除接口
     */
    @DeleteMapping("batchRemove/{ids}")
    @Operation(summary = "批量删除用户")
    @PreAuthorize("hasPermission('/system/user/remove','sys:user:remove')")
    @Logging("删除用户", describe = "删除用户", type = BusinessType.REMOVE)
    fun batchRemove(@PathVariable ids: String) = boolResult {
        sysUserService.batchRemove(ids.split(","))
    }

    /**
     * 用户删除接口
     */
    @Transactional(rollbackFor = [Exception::class])
    @DeleteMapping("remove/{id}")
    @Operation(summary = "删除用户数据")
    @PreAuthorize("hasPermission('/system/user/remove','sys:user:remove')")
    @Logging("删除用户", describe = "删除用户", type = BusinessType.REMOVE)
    fun remove(@PathVariable id: String) = boolResult {
        sysUserService.remove(id)
    }

    /**
     * 根据 userId 开启用户
     */
    @PutMapping("enable")
    @Operation(summary = "开启用户登录")
    fun enable(@RequestBody sysUser: SysUser) = boolResult {
        sysUser.enable = true
        sysUserService.modify(sysUser)
    }

    /**
     * 根据 userId 禁用用户
     */
    @PutMapping("disable")
    @Operation(summary = "禁用用户登录")
    fun disable(@RequestBody sysUser: SysUser) = boolResult {
        sysUser.enable = false
        sysUserService.modify(sysUser)
    }

    /**
     * 用户修改接口
     */
    @PutMapping("updateInfo")
    @Operation(summary = "修改用户数据")
    fun updateInfo(@RequestBody sysUser: SysUser) = boolResult {
        sysUserService.modify(sysUser)
    }

    class EditPassword {
        var userId: String = ""

        /**
         * 旧密码
         */
        var oldPassword: String = ""

        /**
         * 新密码
         */
        var newPassword: String = ""

        /**
         * 确认密码
         */
        var confirmPassword: String = ""
    }
}