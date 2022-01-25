package top.vuhe.admin.system.service.impl

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysDictData
import top.vuhe.admin.system.mapper.SysDictDataMapper
import top.vuhe.admin.system.service.ISysDictDataService

/**
 * 字典值服务实现类
 *
 * @author vuhe
 */
@Service
class SysDictDataServiceImpl(
    private val sysDictDataMapper: SysDictDataMapper,
    private val database: Database
) : CurdService<SysDictData>(sysDictDataMapper), ISysDictDataService {

    override fun selectByCode(typeCode: String): List<SysDictData> {
        return sysDictDataMapper.selectByCode(typeCode)
    }

    override fun queryTableDictItemsByCode(table: String, text: String, code: String): List<SysDictData> {
        val sql = """
                select $text, $code from $table
            """.trimIndent()
        return queryDictList(sql)
    }

    override fun queryTableDictItemsByCodeAndFilter(
        table: String, text: String, code: String, filterSql: String
    ): List<SysDictData> {
        val where = if (filterSql.isNotEmpty()) "where $filterSql" else ""
        val sql = """
                select $text, $code from $table $where
            """.trimIndent()
        return queryDictList(sql)
    }

    override fun queryTableDictByKeys(
        table: String, text: String, code: String, keyArray: List<String>
    ): List<SysDictData> {
        val where = keyArray.joinToString(prefix = "(", separator = ",", postfix = ")")
        val sql = """
                select $text, $code from $table where $code in $where 
            """.trimIndent()
        return queryDictList(sql)
    }

    private fun queryDictList(sql: String): List<SysDictData> {
        return database.useConnection { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.executeQuery().asIterable().map {
                    SysDictData().apply {
                        dataLabel = it.getString(1)
                        dataValue = it.getString(2)
                    }
                }
            }
        }
    }
}
