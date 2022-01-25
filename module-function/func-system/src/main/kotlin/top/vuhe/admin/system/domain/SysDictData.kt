package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 字典值领域模型
 *
 * @author vuhe
 */
class SysDictData : BaseEntity() {
    override val id: String get() = dataId

    /**
     * id 编号
     */
    var dataId: String = ""

    /**
     * 字典显示
     */
    var dataLabel: String = ""

    /**
     * 字典值
     */
    var dataValue: String = ""

    /**
     * 字典类型
     */
    var typeCode: String = ""

    /**
     * 是否为默认
     */
    var isDefault: String = ""

    /**
     * 是否启用
     */
    var enable: Boolean? = null
}
