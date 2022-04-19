package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.TreeRepository
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.table.SysDeptTable

/**
 * 部门接口
 *
 * @author vuhe
 */
@Repository
class SysDeptRepository : TreeRepository<SysDeptTable, SysDept>() {

    override val table get() = SysDeptTable

    override var SysDept.entityId: String by SysDept::deptId

    override fun countChildren(id: String): Int {
        return entities.filter { it.parentId eq id }.count()
    }
}
