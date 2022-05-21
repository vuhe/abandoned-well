package top.vuhe.domain.power

import org.ktorm.entity.Entity

/**
 * ### 权限领域模型
 *
 * @author vuhe
 */
interface SysPower : Entity<SysPower> {
    /** 编号 */
    var powerId: String

    /** 权限名称 */
    val powerName: String

    /** 类型 */
    val powerType: String

    /** 标识 */
    val powerCode: String

    /** 路径 */
    val powerUrl: String

    /** 打开方式 */
    val openType: String

    /** 父级编号 */
    val parentId: String

    /** 图标 */
    val icon: String

    /** 排序 */
    val sort: Int

    /** 开启 */
    var enable: Boolean

    /** 前端显示标识 */
    var checkArr: String
}
