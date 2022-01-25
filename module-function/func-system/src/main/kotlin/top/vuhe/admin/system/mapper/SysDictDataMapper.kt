package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysDictData

/**
 * 字典数据接口
 *
 * @author vuhe
 */
@Repository
class SysDictDataMapper : CurdMapper<SysDictData>("sys_dict_data") {
    override val id = varchar("data_id").primaryKey().bind(SysDictData::dataId)
    private val dataLabel = varchar("data_label").bind(SysDictData::dataLabel)
    private val dataValue = varchar("data_value").bind(SysDictData::dataValue)
    private val typeCode = varchar("type_code").bind(SysDictData::typeCode)
    private val isDefault = varchar("is_default").bind(SysDictData::isDefault)
    private val enable = boolean("enable").bind(SysDictData::enable, true)

    private val createTime = datetime("create_time").bind(SysDictData::createTime)
    private val createBy = varchar("create_by").bind(SysDictData::createBy)
    private val updateTime = datetime("update_time").bind(SysDictData::updateTime)
    private val updateBy = varchar("update_by").bind(SysDictData::updateBy)
    private val remark = varchar("remark").bind(SysDictData::remark)

    override fun Query.listFilter(param: SysDictData): Query {
        return whereWithConditions {
            if (param.typeCode.isNotEmpty()) it.add(typeCode eq param.typeCode)
        }.orderBy(dataValue.asc())
    }

    /**
     * 通过 code 查询列表
     */
    fun selectByCode(typeCode: String): List<SysDictData> {
        return database.from(this).select().where { this.typeCode eq typeCode }
            .orderBy(dataValue.asc()).map { createEntity(it) }
    }

    /**
     * 通过 typeCode 删除
     */
    fun deleteByCodes(typeCode: List<String>): Int {
        if (typeCode.isEmpty()) return 0
        return database.delete(this) { this.typeCode inList  typeCode }
    }
}
