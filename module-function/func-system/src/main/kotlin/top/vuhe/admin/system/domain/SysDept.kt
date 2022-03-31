package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 字典值领域模型
 *
 * @author vuhe
 */
class SysDept : BaseEntity() {
    /** 部门编号 */
    var deptId by varchar("dept_id").primary()

    /** 部门名称 */
    var deptName by varchar("dept_name")

    /** 部门地址 */
    var address by varchar("address")

    /** 父级编号 */
    var parentId by varchar("parent_id")

    /** 负责人 */
    var leader by varchar("leader")

    /** 手机号 */
    var phone by varchar("phone")

    /** 邮箱 */
    var email by varchar("email")

    /** 状态 */
    var enable by boolean("status")

    /** 排序 */
    var sort by int("sort")
}
