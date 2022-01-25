package top.vuhe.admin.generator.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 业务表实体
 *
 * @author vuhe
 */
class GenTable : BaseEntity() {
    override val id: String get() = tableId

    /** 编号  */
    var tableId: String = ""

    /** 表名称  */
    var tableName: String = ""

    /** 表描述  */
    var tableComment: String = ""

    /** 关联父表的表名  */
    var subTableName: String = ""

    /** 本表关联父表的外键名  */
    var subTableFkName: String = ""

    /** 实体类名称(首字母大写)  */
    var className: String = ""

    /** 使用的模板（crud单表操作 tree树表操作 sub主子表操作）  */
    var tplCategory: String = ""

    /** 生成包路径  */
    var packageName: String = ""

    /** 生成模块名  */
    var moduleName: String = ""

    /** 生成业务名  */
    var businessName: String = ""

    /** 生成功能名  */
    var functionName: String = ""

    /** 生成作者  */
    var functionAuthor: String = ""

    /** 生成代码方式（0zip压缩包 1自定义路径）  */
    var genType: String = ""

    /** 生成路径（不填默认项目路径）  */
    var genPath: String = ""

    /** 主键信息  */
    var pkColumn: GenTableColumn? = null

    /** 子表信息  */
    var subTable: GenTable? = null

    /** 表列信息  */
    var columns: List<GenTableColumn> = emptyList()

    /** 其它生成选项  */
    var options: String = ""

    /** 树编码字段  */
    var treeCode: String = ""

    /** 树父编码字段  */
    var treeParentCode: String = ""

    /** 树名称字段  */
    var treeName: String = ""

    /** 上级菜单ID字段  */
    var parentMenuId: String = ""

    /** 上级菜单名称字段  */
    var parentMenuName: String = ""

    /** 请求参数 */
    var params: Map<String, Any> = emptyMap()

    val isSub: Boolean
        get() = "sub" == tplCategory

    val isTree: Boolean
        get() = "tree" == tplCategory

    val isCurd: Boolean
        get() = "crud" == tplCategory

//    fun isSuperColumn(javaField: String?): Boolean {
//        return isSuperColumn(tplCategory, javaField)
//    }
//
//    companion object {
//        fun isSub(tplCategory: String?): Boolean {
//            return tplCategory != null && StringUtils.equals(GenerateConstant.TPL_SUB, tplCategory)
//        }
//
//        fun isTree(tplCategory: String?): Boolean {
//            return tplCategory != null && StringUtils.equals(GenerateConstant.TPL_TREE, tplCategory)
//        }
//
//        fun isCrud(tplCategory: String?): Boolean {
//            return tplCategory != null && StringUtils.equals(GenerateConstant.TPL_CRUD, tplCategory)
//        }
//
//        fun isSuperColumn(tplCategory: String?, javaField: String?): Boolean {
//            return if (isTree(tplCategory)) {
//                StringUtils.equalsAnyIgnoreCase(
//                    javaField,
//                    ArrayUtils.addAll(GenerateConstant.TREE_ENTITY, GenerateConstant.BASE_ENTITY)
//                )
//            } else StringUtils.equalsAnyIgnoreCase(javaField, GenerateConstant.BASE_ENTITY)
//        }
//    }
}