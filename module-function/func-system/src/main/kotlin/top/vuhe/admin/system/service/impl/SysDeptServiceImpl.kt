package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.mapper.SysDeptMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import top.vuhe.admin.system.service.ISysDeptService

/**
 * 部门服务接口实现
 *
 * @author vuhe
 */
@Service
class SysDeptServiceImpl : CurdService<SysDept>(SysDeptMapper), ISysDeptService {
    private val sysUserMapper = SysUserMapper
    private val sysDeptMapper = SysDeptMapper

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 查出对应的用户，并清除部门
        sysUserMapper.batchDeleteDept(ids)
        // 清除部门
        sysDeptMapper.batchDelete(ids)
        return true
    }

    override fun getByParentId(parentId: String): List<SysDept> {
        return sysDeptMapper.selectListByParentId(parentId)
    }
}
