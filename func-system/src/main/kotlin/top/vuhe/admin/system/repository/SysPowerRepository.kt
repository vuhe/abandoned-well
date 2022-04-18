package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.table.SysPowerTable

/**
 * 系统权限接口
 *
 * @author vuhe
 */
@Repository
class SysPowerRepository : CurdRepository<SysPowerTable, SysPower>(cacheable = true) {

    override val table get() = SysPowerTable

    override var SysPower.entityId: String by SysPower::powerId

    /**
     * 计算子节点个数
     */
    fun countChildren(id: String): Int {
        return entities.filter { it.parentId eq id }.count()
    }

    /**
     * 通过 id 列表查询
     */
    fun selectListByIds(ids: List<String>): List<SysPower> {
        return ids.asSequence().distinct()
            .mapNotNull { selectById(it) }
            .filter { it.enable }
            .toList()
    }

    /**
     * 通过 admin 查询列表
     */
    fun selectListByAdmin(): List<SysPower> {
        return selectList().filter { it.enable }
    }
}
