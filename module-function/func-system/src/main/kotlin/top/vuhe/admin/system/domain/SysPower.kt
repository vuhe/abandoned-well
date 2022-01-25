package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * ### 权限领域模型
 *
 * @author vuhe
 */
class SysPower : BaseEntity() {
    override val id: String get() = powerId

    /**
     * 编号
     */
    var powerId: String = ""

    /**
     * 权限名称
     */
    var powerName: String = ""

    /**
     * 类型
     */
    var powerType: String = ""

    /**
     * 标识
     */
    var powerCode: String = ""

    /**
     * 路径
     */
    var powerUrl: String = ""

    /**
     * 打开方式
     */
    var openType: String = ""

    /**
     * 父级编号
     */
    var parentId: String = ""

    /**
     * 图标
     */
    var icon: String= ""

    /**
     * 排序
     */
    var sort: Int? = null

    /**
     * 开启
     */
    var enable: Boolean? = null

    /**
     * 计算列 提供给前端组件
     *
     * default: "0"
     */
    var checkArr: String = "0"
}
