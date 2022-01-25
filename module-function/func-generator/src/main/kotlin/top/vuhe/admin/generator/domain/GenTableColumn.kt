package top.vuhe.admin.generator.domain

import top.vuhe.admin.spring.database.entity.BaseEntity
import java.util.*

/**
 * 业务表字段实体
 *
 * @author vuhe
 */
class GenTableColumn : BaseEntity() {
    override val id: String get() = columnId

    /** 编号  */
    var columnId: String = ""

    /** 归属表编号  */
    var tableId: String = ""

    /** 列名称  */
    var columnName: String = ""

    /** 列描述  */
    var columnComment: String = ""

    /** 列类型  */
    var columnType: TypeMapping? = null

    /** JAVA字段名  */
    var javaField: String = ""

    /** 是否主键（1是）  */
    var isPk: Boolean? = null

    /** 是否自增（1是）  */
    var isIncrement: Boolean? = null

    /** 是否必填（1是）  */
    var isRequired: Boolean? = null

    /** 是否为插入字段（1是）  */
    var isInsert: Boolean? = null

    /** 是否编辑字段（1是）  */
    var isEdit: Boolean? = null

    /** 是否列表字段（1是）  */
    var isList: Boolean? = null

    /** 是否查询字段（1是）  */
    var isQuery: Boolean? = null

    /** 查询方式 */
    var queryType: QueryType? = QueryType.EQ

    /** 显示类型
     * （input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、upload上传控件）
     */
    var htmlType: String = ""

    /** 字典类型  */
    var dictType: String = ""

    /** 排序  */
    var sort: Int? = null

    val capJavaField: String
        get() = javaField.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

    val isSuperColumn: Boolean
        get() = javaField in listOf(
            "createBy", "createTime", "updateBy", "updateTime", "remark",
            "parentName", "parentId", "orderNum", "ancestors"
        )

    // 用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
    val isUsableColumn: Boolean
        get() = javaField in listOf("parentId", "orderNum")
//
//    fun readConverterExp(): String? {
//        val remarks: String = StringUtils.substringBetween(columnComment, "（", "）")
//        val sb = StringBuffer()
//        return if (StringUtils.isNotEmpty(remarks)) {
//            for (value in remarks.split(CommonConstant.SPACE).toTypedArray()) {
//                if (StringUtils.isNotEmpty(value)) {
//                    val startStr: Any = value.subSequence(0, 1)
//                    val endStr = value.substring(1)
//                    sb.append("").append(startStr).append("=").append(endStr).append(",")
//                }
//            }
//            sb.deleteCharAt(sb.length - 1).toString()
//        } else {
//            columnComment
//        }
//    }
}