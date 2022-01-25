package top.vuhe.admin.spring.database.entity

import java.time.LocalDateTime

/**
 * ### 基础实体类
 * 本类字段可能存在不使用的映射
 *
 * @author vuhe
 */
abstract class BaseEntity {
    /**
     * 唯一标识 id
     */
    abstract val id: String

    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null

    /**
     * 创建人
     */
    var createBy: String = ""

    /**
     * 修改时间
     */
    var updateTime: LocalDateTime? = null

    /**
     * 修改人
     */
    var updateBy: String = ""

    /**
     * 备注
     */
    var remark: String = ""
}
