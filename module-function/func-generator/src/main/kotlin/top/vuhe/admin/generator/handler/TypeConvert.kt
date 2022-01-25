package top.vuhe.admin.generator.handler

import top.vuhe.admin.api.constant.BRACKET_LEFT
import top.vuhe.admin.generator.domain.TypeMapping

/**
 * ### 类型转换器
 * 数据库类型 -> Kotlin 类型
 *
 * @author vuhe
 */
object TypeConvert {
    private val intList = listOf("smallint", "mediumint", "int", "integer")
    private val shortList = listOf("tinyint")
    private val longList = listOf("bigint", "money")
    private val floatList = listOf("float")
    private val doubleList = listOf("double", "real")
    private val decimalList = listOf("decimal", "number")
    private val varcharList = listOf("nchar", "char", "varchar", "nvarchar", "varchar2")
    private val textList = listOf("ntext", "tinytext", "text", "mediumtext", "longtext")
    private val blobList = listOf(
        "tinyblob", "blob", "mediumtblob", "longblob",
        "binary", "varbinary", "image", "bit"
    )
    private val timestampList = listOf("timestamp")
    private val datetimeList = listOf("datetime", "smalldatetime", "datetime2", "datetimeoffset")
    private val dateList = listOf("date")
    private val timeList = listOf("time")

    /**
     * 数据库 type string -> [TypeMapping]；
     * 部分映射无法自动识别，需要自己设置
     */
    operator fun invoke(dbType: String): TypeMapping? = when (filterType(dbType)) {
        in varcharList -> TypeMapping.Varchar
        in intList -> TypeMapping.Int
        in datetimeList -> TypeMapping.Datetime
        in shortList -> TypeMapping.Short
        in longList -> TypeMapping.Long
        in floatList -> TypeMapping.Float
        in doubleList -> TypeMapping.Double
        in decimalList -> TypeMapping.Decimal
        in textList -> TypeMapping.Text
        in blobList -> TypeMapping.Blob
        in timestampList -> TypeMapping.Timestamp
        in dateList -> TypeMapping.Date
        in timeList -> TypeMapping.Time
        else -> null
    }

    /**
     * 过滤数据库类型，删除 ()
     */
    private fun filterType(dbType: String) = if (dbType.indexOf(BRACKET_LEFT) > 0) {
        dbType.substringBefore(BRACKET_LEFT)
    } else dbType
}
