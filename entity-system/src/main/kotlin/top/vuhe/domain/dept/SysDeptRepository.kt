package top.vuhe.domain.dept

import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.springframework.stereotype.Repository
import top.vuhe.database.repository.TreeRepository

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
