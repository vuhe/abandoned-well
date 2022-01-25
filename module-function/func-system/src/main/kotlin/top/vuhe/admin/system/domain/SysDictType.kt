package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 字典类型领域模型
 *
 * @author vuhe
 */
class SysDictType: BaseEntity() {
    /**
     * 标识
     */
    override var id: String = ""

    /**
     * 字典名称
     */
    var typeName: String = ""

    /**
     * 字典类型
     */
    var typeCode: String = ""

    /**
     * 字典描述
     */
    var description: String = ""

    /**
     * 字典可用状态
     */
    var enable: Boolean? = null
}
