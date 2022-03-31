package top.vuhe.admin.system.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysDept

/**
 * 部门接口
 *
 * @author vuhe
 */
@Repository
@Suppress("unused")
class SysDeptMapper : CurdMapper<SysDept>("sys_dept") {
    override val id = varchar("dept_id").primaryKey().bind(SysDept::deptId)
    private val deptName = varchar("dept_name").bind(SysDept::deptName)
    private val address = varchar("address").bind(SysDept::address)
    private val parentId = varchar("parent_id").bind(SysDept::parentId)
    private val leader = varchar("leader").bind(SysDept::leader)
    private val phone = varchar("phone").bind(SysDept::phone)
    private val email = varchar("email").bind(SysDept::email)
    private val enable = boolean("status").bind(SysDept::enable, true)
    private val sort = int("sort").bind(SysDept::sort, 0)

    override fun Query.listFilter(param: SysDept): Query {
        return whereWithConditions {
            if (param.deptName.isNotEmpty()) it.add(deptName like "%${param.deptName}%")
        }
    }

    /**
     * 根据 parentId 查询部门
     */
    fun selectListByParentId(id: String): List<SysDept> {
        return database.from(this).select().where { parentId eq id }
            .map { createEntity(it) }
    }
}
