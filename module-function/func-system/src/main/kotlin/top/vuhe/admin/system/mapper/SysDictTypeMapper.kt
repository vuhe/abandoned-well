package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysDictType

/**
 * 字典类型接口
 *
 * @author vuhe
 */
@Repository
class SysDictTypeMapper : CurdMapper<SysDictType>("sys_dict_type") {
    override val id = varchar("id").primaryKey().bind(SysDictType::id)
    private val typeName = varchar("type_name").bind(SysDictType::typeName)
    private val typeCode = varchar("type_code").bind(SysDictType::typeCode)
    private val description = varchar("description").bind(SysDictType::description)
    private val enable = boolean("enable").bind(SysDictType::enable, true)

    private val createTime = datetime("create_time").bind(SysDictType::createTime)
    private val createBy = varchar("create_by").bind(SysDictType::createBy)
    private val updateTime = datetime("update_time").bind(SysDictType::updateTime)
    private val updateBy = varchar("update_by").bind(SysDictType::updateBy)
    private val remark = varchar("remark").bind(SysDictType::remark)

    override fun Query.listFilter(param: SysDictType): Query {
        return whereWithConditions {
            if (param.typeName.isNotEmpty()) it.add(typeName eq param.typeName)
        }
    }

    /**
     * 通过 id 列表查询实体
     */
    fun selectListByIds(ids: List<String>): List<SysDictType> {
        return database.from(this).select().where { id inList ids }.map { createEntity(it) }
    }
}
