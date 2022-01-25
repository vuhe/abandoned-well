package top.vuhe.admin.generator.mapper

import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.generator.domain.GenTableColumn
import top.vuhe.admin.generator.domain.QueryType
import top.vuhe.admin.generator.domain.TypeMapping
import top.vuhe.admin.generator.handler.TypeConvert
import top.vuhe.admin.spring.database.mapper.CurdMapper

/**
 * 业务表字段接口
 *
 * @author vuhe
 */
@Repository
class GenTableColumnMapper : CurdMapper<GenTableColumn>("gen_table_column") {
    override val id = varchar("column_id").primaryKey().bind(GenTableColumn::columnId)
    private val tableId = varchar("table_id").bind(GenTableColumn::tableId)
    private val columnName = varchar("column_name").bind(GenTableColumn::columnName)
    private val columnComment = varchar("column_comment").bind(GenTableColumn::columnComment)
    private val columnType = enum<TypeMapping>("column_type").bind(GenTableColumn::columnType)
    private val ktField = varchar("kotlin_field").bind(GenTableColumn::javaField)
    private val isPk = boolean("is_pk").bind(GenTableColumn::isPk)
    private val isIncrement = boolean("is_increment").bind(GenTableColumn::isIncrement)
    private val isRequired = boolean("is_required").bind(GenTableColumn::isRequired)
    private val isEdit = boolean("is_edit").bind(GenTableColumn::isEdit)
    private val isList = boolean("is_list").bind(GenTableColumn::isList)
    private val isQuery = boolean("is_query").bind(GenTableColumn::isQuery)
    private val queryType = enum<QueryType>("query_type").bind(GenTableColumn::queryType)
    private val htmlType = varchar("html_type").bind(GenTableColumn::htmlType)
    private val dictType = varchar("dict_type").bind(GenTableColumn::dictType)
    private val sort = int("sort").bind(GenTableColumn::sort)

    private val createTime = datetime("create_time").bind(GenTableColumn::createTime)
    private val createBy = varchar("create_by").bind(GenTableColumn::createBy)
    private val updateTime = datetime("update_time").bind(GenTableColumn::updateTime)
    private val updateBy = varchar("update_by").bind(GenTableColumn::updateBy)

    override fun Query.listFilter(param: GenTableColumn): Query {
        return orderBy(sort.asc())
    }

    /**
     * 通过 table id 查询
     */
    fun selectByTableId(tableId: String): List<GenTableColumn> {
        return database.from(this).select().where { this.tableId eq tableId }
            .orderBy(sort.asc()).map { createEntity(it) }
    }

    /**
     * 通过 table id 批量删除
     */
    fun batchDeleteByTableId(ids: List<String>): Int {
        if (ids.isEmpty()) return 0
        return database.delete(this) { tableId inList ids }
    }

    /**
     * 从数据获取基本字段信息
     */
    fun selectTableColumnByName(tableName: String): List<GenTableColumn> {
        return database.useConnection { conn ->
            val sql = """select column_name,
                (if((is_nullable = 'no' && column_key != 'PRI'), true, false)) as is_required,
                (if(column_key = 'PRI', true, false))                          as is_pk,
                ordinal_position                                               as sort,
                column_comment,
                (if(extra = 'auto_increment', true, false))                    as is_increment,
                column_type
                from information_schema.columns
                where table_schema = (select database()) and table_name = '$tableName'
                order by ordinal_position;
            """.trimIndent()
            conn.prepareStatement(sql).use { statement ->
                statement.executeQuery().asIterable().map {
                    GenTableColumn().apply {
                        columnName = it.getString(1) ?: ""
                        isRequired = it.getBoolean(2)
                        isPk = it.getBoolean(3)
                        sort = it.getInt(4)
                        columnComment = it.getString(5) ?: ""
                        isIncrement = it.getBoolean(6)
                        columnType = TypeConvert(it.getString(7) ?: "")
                    }
                }
            }
        }
    }
}
