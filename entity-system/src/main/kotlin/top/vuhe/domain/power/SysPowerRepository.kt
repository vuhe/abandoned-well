package top.vuhe.domain.power

import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.springframework.stereotype.Repository
import top.vuhe.database.repository.TreeRepository

/**
 * 系统权限接口
 *
 * @author vuhe
 */
@Repository
class SysPowerRepository : TreeRepository<SysPowerTable, SysPower>() {

    override val cacheName: String? get() = table.tableName

    override val table get() = SysPowerTable

    override var SysPower.entityId: String by SysPower::powerId

    override fun countChildren(id: String): Int {
        return entities.filter { it.parentId eq id }.count()
    }

}
