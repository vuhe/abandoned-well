package top.vuhe.admin.generator.mapper

import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.spring.database.mapper.CurdMapper
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * 业务表接口
 *
 * @author vuhe
 */
@Repository
class GenTableMapper : CurdMapper<GenTable>("gen_table") {
    override val id = varchar("table_id").primaryKey().bind(GenTable::tableId)
    private val genTableName = varchar("table_name").bind(GenTable::tableName)
    private val tableComment = varchar("table_comment").bind(GenTable::tableComment)
    private val subTableName = varchar("sub_table_name").bind(GenTable::subTableName)
    private val subTableFkName = varchar("sub_table_fk_name").bind(GenTable::subTableFkName)
    private val className = varchar("class_name").bind(GenTable::className)
    private val tplCategory = varchar("tpl_category").bind(GenTable::tplCategory)
    private val packageName = varchar("package_name").bind(GenTable::packageName)
    private val moduleName = varchar("module_name").bind(GenTable::moduleName)
    private val businessName = varchar("business_name").bind(GenTable::businessName)
    private val functionName = varchar("function_name").bind(GenTable::functionName)
    private val functionAuthor = varchar("function_author").bind(GenTable::functionAuthor)
    private val genType = varchar("gen_type").bind(GenTable::genType)
    private val genPath = varchar("gen_path").bind(GenTable::genPath)
    private val options = varchar("options").bind(GenTable::options)
    private val parentMenuId = varchar("parent_menu_id").bind(GenTable::parentMenuId)
    private val parentMenuName = varchar("parent_menu_name").bind(GenTable::parentMenuName)
    private val treeCode = varchar("tree_code").bind(GenTable::treeCode)
    private val treeParentCode = varchar("tree_parent_code").bind(GenTable::treeParentCode)

    private val createTime = datetime("create_time").bind(GenTable::createTime)
    private val createBy = varchar("create_by").bind(GenTable::createBy)
    private val updateTime = datetime("update_time").bind(GenTable::updateTime)
    private val updateBy = varchar("update_by").bind(GenTable::updateBy)
    private val remark = varchar("remark").bind(GenTable::remark)

    override fun Query.listFilter(param: GenTable): Query {
        return whereWithConditions {
            if (param.tableName.isNotEmpty()) it.add(genTableName like "%${param.tableName}%")
            if (param.tableComment.isNotEmpty()) it.add(tableComment like "%${param.tableComment}%")
        }
    }

    /**
     * 根据 tableName 查询实体
     */
    fun selectByName(tableName: String): GenTable? {
        val list = database.from(this).select()
            .where { genTableName eq tableName }
            .map { createEntity(it) }
        return list.getOrNull(0)
    }

    /**
     * 直接查询数据库中所有的表
     */
    fun selectTable(entity: GenTable): List<GenTable> {
        val base = "and table_name not in (select table_name from gen_table)"
        val name = if (entity.tableName.isNotEmpty()) "and table_name like %${entity.tableName}%" else ""
        val comment = if (entity.tableComment.isNotEmpty()) "and table_comment like %${entity.tableComment}%" else ""
        return selectDatabase("$base $name $comment")
    }

    /**
     * 按名称查询数据库中所有的表
     */
    fun selectTableByName(names: List<String>): List<GenTable> {
        val base = "and table_name in ${names.joinToString(prefix = "(", postfix = ")")}"
        return selectDatabase(base)
    }

    private fun selectDatabase(where: String): List<GenTable> {
        return database.useConnection { conn ->
            val sql = """select table_name, table_comment, create_time, update_time 
                from information_schema.tables
                where table_schema = (select database())
                and table_name not like 'qrtz_%' and table_name not like 'gen_%'
                $where
                order by table_name desc;
            """.trimIndent()
            conn.prepareStatement(sql).use { statement ->
                statement.executeQuery().asIterable().map {
                    val zoneId = ZoneId.systemDefault()
                    GenTable().apply {
                        tableName = it.getString(1)
                        tableComment = it.getString(2)
                        createTime = LocalDateTime.ofInstant(it.getDate(3).toInstant(), zoneId)
                        updateTime = LocalDateTime.ofInstant(it.getDate(4).toInstant(), zoneId)
                    }
                }
            }
        }
    }
}
