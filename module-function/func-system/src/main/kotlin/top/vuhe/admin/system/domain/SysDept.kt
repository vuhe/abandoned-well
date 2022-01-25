package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 字典值领域模型
 *
 * @author vuhe
 */
class SysDept : BaseEntity() {
    override val id: String get() = deptId

    /**
     * 部门编号
     */
    var deptId: String = ""

    /**
     * 部门名称
     */
    var deptName: String = ""

    /**
     * 部门地址
     */
    var address: String = ""

    /**
     * 父级编号
     */
    var parentId: String = ""

    /**
     * 负责人
     */
    var leader: String = ""

    /**
     * 手机号
     */
    var phone: String = ""

    /**
     * 邮箱
     */
    var email: String = ""

    /**
     * 状态
     */
    var enable: Boolean? = null

    /**
     * 排序
     */
    var sort: Int? = null
}
