package top.vuhe.admin.generator.handler

import org.apache.commons.lang3.RegExUtils
import top.vuhe.admin.api.constant.BRACKET_LEFT
import top.vuhe.admin.api.constant.BRACKET_RIGHT
import top.vuhe.admin.api.text.localeLowercase
import top.vuhe.admin.api.text.localeUppercase
import top.vuhe.admin.api.text.substring
import top.vuhe.admin.api.text.toCamelCase
import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.generator.domain.QueryType
import top.vuhe.admin.generator.domain.TypeMapping
import top.vuhe.admin.generator.property.GeneratorProperty

/**
 * 生成信息转换器
 *
 * @author vuhe
 */
object GenInfoConvert {
    /** 页面不需要编辑字段  */
    private val notEditField = listOf("id", "create_by", "create_time", "del_flag")

    /** 页面不需要显示的列表字段  */
    private val notListField = arrayOf(
        "id", "create_by", "create_time", "del_flag", "update_by",
        "update_time"
    )

    /** 页面不需要查询字段  */
    private val notQueryField = arrayOf(
        "id", "create_by", "create_time", "del_flag", "update_by",
        "update_time", "remark"
    )

    /**
     * 初始化表信息
     */
    fun GenTable.init() {
        className = tableName.convertClassName()
        packageName = GeneratorProperty.packageName
        moduleName = GeneratorProperty.packageName.getModuleName()
        businessName = tableName.getBusinessName()
        functionName = tableComment.replaceText()
        functionAuthor = GeneratorProperty.author
    }

    /**
     * 初始化列属性字段
     */
    fun GenTableColumn.init(table: GenTable) {
        tableId = table.tableId
        htmlType = when (columnType) {
            TypeMapping.Varchar -> "input"
            TypeMapping.Text -> "textarea"
            TypeMapping.Boolean -> "radio"
            TypeMapping.Datetime, TypeMapping.Date, TypeMapping.Time -> "datetime"
            TypeMapping.Int, TypeMapping.Short, TypeMapping.Long,
            TypeMapping.Float, TypeMapping.Double, TypeMapping.Decimal -> "input"
            else -> ""
        }
        javaField = columnName.toCamelCase()

        // 插入字段（默认所有字段都需要插入）
        isInsert = true

        // 编辑字段
        isEdit = columnName !in notEditField && isPk != true

        // 列表字段
        isList = columnName !in notListField && isPk != true

        // 查询字段
        isQuery = columnName !in notQueryField && isPk != true

        // 查询字段类型
        if (columnName.endsWith("name", ignoreCase = true)) {
            queryType = QueryType.LIKE
        }

        // 状态字段设置单选框
        htmlType = when {
            columnName.endsWith("status", true) -> "radio"
            columnName.endsWith("type", true) -> "select"
            columnName.endsWith("sex", true) -> "select"
            columnName.endsWith("file", true) -> "upload"
            else -> htmlType
        }
    }

    /**
     * 获取模块名
     *
     * @receiver 包名
     * @return 模块名
     */
    private fun String.getModuleName(): String {
        val lastIndex = lastIndexOf(".")
        val nameLength = length
        return substring(lastIndex + 1, nameLength)
    }

    /**
     * 获取业务名
     *
     * @receiver 表名
     * @return 业务名
     */
    private fun String.getBusinessName(): String {
        val lastIndex = indexOf("_")
        val nameLength = length
        val end: String = substring(lastIndex + 1, nameLength)
        val arr = end.split("_").toTypedArray()
        val businessName = arr.fold(StringBuilder()) { sb, it ->
            sb.append(it.upperCaptureName())
        }.toString()
        return businessName.lowerCaptureName()
    }

    /**
     * 首字母小写
     */
    private fun String.lowerCaptureName(): String {
        return substring(0, 1).localeLowercase() + substring(1)
    }

    /**
     * 首字母大写
     */
    private fun String.upperCaptureName(): String {
        return substring(0, 1).localeUppercase() + substring(1)
    }

    /**
     * 表名转换成Java类名
     *
     * @receiver 表名称
     * @return 类名
     */
    private fun String.convertClassName(): String {
        var tableName = this
        val autoRemovePre = GeneratorProperty.autoRemovePre
        val tablePrefix = GeneratorProperty.tablePrefix
        if (autoRemovePre && tablePrefix.isNotEmpty()) {
            val searchList = tablePrefix.split(",")
            tableName = tableName.replaceFirst(searchList)
        }
        return tableName.toCamelCase()
    }

    /**
     * 批量替换前缀
     *
     * @receiver 替换值
     * @param searchList 替换列表
     */
    private fun String.replaceFirst(searchList: List<String>): String {
        var text = this
        for (searchString in searchList) {
            if (startsWith(searchString)) {
                text = replaceFirst(searchString.toRegex(), "")
                break
            }
        }
        return text
    }

    /**
     * 关键字替换
     *
     * @return 替换后的名字
     */
    private fun String.replaceText(): String {
        return RegExUtils.replaceAll(this, "(?:表|若依)", "")
    }

    /**
     * 获取字段长度
     *
     * @receiver 列类型
     * @return 截取后的列类型
     */
    private fun String.getColumnLength(): Int {
        return if (indexOf(BRACKET_LEFT) > 0) {
            substring(BRACKET_LEFT, BRACKET_RIGHT).toIntOrNull() ?: 0
        } else 0
    }
}
