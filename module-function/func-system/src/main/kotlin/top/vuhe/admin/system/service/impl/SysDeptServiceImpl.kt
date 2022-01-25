package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
class SysDeptServiceImpl(
    private val sysDeptMapper: SysDeptMapper,
    private val sysUserMapper: SysUserMapper
) : ISysDeptService {

    override fun list(param: SysDept): List<SysDept> {
        return sysDeptMapper.selectList(param)
    }

    override fun add(entity: SysDept): Boolean {
        return sysDeptMapper.insert(entity) > 0
    }

    override fun getOneById(id: String): SysDept? {
        return sysDeptMapper.selectById(id)
    }

    override fun modify(entity: SysDept): Boolean {
        return sysDeptMapper.update(entity) > 0
    }

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
