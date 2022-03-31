package top.vuhe.admin.system.domain

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * ### 用户领域模型
 *
 * @author vuhe
 */
class SysUser : BaseEntity() {
    /** 编号 */
    var userId by varchar("user_id").primary()

    /** 账户 */
    var username by varchar("username")

    /** 密码 */
    var password by varchar("password").insertBefore {
        BCryptPasswordEncoder().encode(it)
    }

    /** 姓名 */
    var realName by varchar("real_name")

    /** 邮箱 */
    var email by varchar("email")

    /** 性别 */
    var sex by varchar("sex")

    /** 电话 */
    var phone by varchar("phone")

    /** 所属部门 */
    var deptId by varchar("dept_id")

    /** 是否锁定 */
    var unlocked by boolean("status")

    /** 是否启用 */
    var enable by boolean("enable")

    /** 是否为管理员 */
    val admin by boolean("admin")

    /** 最后一次登录时间 */
    var lastTime by datetime("last_time").nullable()

    /** 注册时间 */
    var createTime by datetime("create_time")

    /** 备注 */
    var remark by varchar("remark")

    /**
     * 角色 ids，用于修改
     */
    var roleIds: String = ""
}
