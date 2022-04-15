package top.vuhe.admin.system.repository

import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.web.request.PageParam
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.param.SysPowerParam
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

    override fun find(params: PageParam) = buildList {
        params as SysPowerParam
        if (params.powerName.isNotBlank()) add(table.powerName like "%${params.powerName}%")
    }

    /**
     * 通过 parentId 列表查询
     */
    fun selectByParentId(parentId: String): List<SysPower> {
        return entities.filter { it.parentId eq parentId }.toList()
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
