package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity
import java.time.LocalDateTime

/**
 * ### 用户领域模型
 *
 * @author vuhe
 */
class SysUser : BaseEntity() {
    override val id: String get() = userId

    /**
     * 编号
     */
    var userId: String = ""

    /**
     * 账户
     */
    var username: String = ""

    /**
     * 密码
     */
    var password: String = ""

    /**
     * 盐
     */
    var salt: String = ""

    /**
     * 姓名
     */
    var realName: String = ""

    /**
     * 邮箱
     */
    var email: String = ""

    /**
     * 头像
     */
    var avatar: String = ""

    /**
     * 性别
     */
    var sex: String = ""

    /**
     * 电话
     */
    var phone: String = ""

    /**
     * 所属部门
     */
    var deptId: String = ""

    /**
     * 是否锁定
     */
    var unlocked: Boolean? = null

    /**
     * 是否启用
     */
    var enable: Boolean? = null

    /**
     * 是否登录
     */
    var login: Boolean? = null

    /**
     * 是否为管理员
     */
    var admin: Boolean? = null

    /**
     * 最后一次登录时间
     */
    var lastTime: LocalDateTime? = null

    /**
     * 角色 ids，用于修改
     */
    var roleIds: String = ""
}
