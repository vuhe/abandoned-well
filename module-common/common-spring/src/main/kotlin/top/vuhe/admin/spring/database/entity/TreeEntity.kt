package top.vuhe.admin.spring.database.entity

/**
 * ## 树结构的实体类
 *
 * @author vuhe
 */
abstract class TreeEntity : BaseEntity() {
    /**
     * 父节点 id
     */
    var parentId: String = ""

    /**
     * 父节点名称
     */
    var parentName: String = ""
}
