package top.vuhe.admin.well.domina

import org.ktorm.entity.Entity

/**
 * 二级水文地质分区代码
 *
 * @author vuhe
 */
interface RegionCode : Entity<RegionCode> {
    /** 主键 id */
    var id: String

    /** 分区代码 */
    val code: String

    /** 分区信息 */
    val remark: String
}
