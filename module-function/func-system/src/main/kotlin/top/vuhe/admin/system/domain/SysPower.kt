package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * ### 权限领域模型
 *
 * @author vuhe
 */
class SysPower : BaseEntity() {
    /** 编号 */
    var powerId by varchar("power_id").primary()

    /** 权限名称 */
    var powerName by varchar("power_name")

    /** 类型 */
    var powerType by varchar("power_type")

    /** 标识 */
    var powerCode by varchar("power_code")

    /** 路径 */
    var powerUrl by varchar("power_url")

    /** 打开方式 */
    var openType by varchar("open_type")

    /** 父级编号 */
    var parentId by varchar("parent_id")

    /** 图标 */
    var icon by varchar("icon")

    /** 排序 */
    var sort by int("sort")

    /** 开启 */
    var enable by boolean("enable")

    /**
     * 计算列 提供给前端组件
     *
     * default: "0"
     */
    var checkArr: String = "0"
}
