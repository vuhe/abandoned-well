package top.vuhe.admin.system.mapper

import org.ktorm.dsl.eq
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.system.domain.SysDept

/**
 * 部门接口
 *
 * @author vuhe
 */
object SysDeptMapper : CurdMapper<SysDept>("sys_dept") {
    /**
     * 根据 parentId 查询部门
     */
    fun selectListByParentId(id: String): List<SysDept> {
        return selectByConditions {
            it.add(col("parent_id") eq id)
        }
    }
}
