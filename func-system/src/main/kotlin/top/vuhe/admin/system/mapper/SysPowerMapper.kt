package top.vuhe.admin.system.mapper

import org.ktorm.dsl.eq
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysPower

/**
 * 系统权限接口
 *
 * @author vuhe
 */
object SysPowerMapper : CurdMapper<SysPower>("sys_power") {
    init {
        enableCache()
    }

    /**
     * 通过 parentId 列表查询
     */
    fun selectByParentId(parentId: String): List<SysPower> {
        return selectByConditions {
            it.add(col("parent_id") eq parentId)
        }
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
