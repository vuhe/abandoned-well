package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.table.SysDeptTable

/**
 * 部门接口
 *
 * @author vuhe
 */
@Repository
class SysDeptRepository : CurdRepository<SysDeptTable, SysDept>() {

    override val table get() = SysDeptTable

    override var SysDept.entityId: String by SysDept::deptId

    /**
     * 计算子节点个数
     */
    fun countChildren(id: String): Int {
        return entities.filter { it.parentId eq id }.count()
    }
}
