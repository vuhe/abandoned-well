package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.repository.SysDeptRepository
import top.vuhe.admin.system.repository.SysUserRepository
import top.vuhe.admin.system.service.ISysDeptService

/**
 * 部门服务接口实现
 *
 * @author vuhe
 */
@Service
class SysDeptServiceImpl(
    private val sysUserRepository: SysUserRepository,
    private val sysDeptRepository: SysDeptRepository
) : CurdService<SysDept>(sysDeptRepository), ISysDeptService {

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 查出对应的用户，并清除部门
        sysUserRepository.dropDept(ids)
        // 清除部门
        sysDeptRepository.delete(ids)
        return true
    }

    override fun hasNoChildNodes(id: String): Boolean {
        return sysDeptRepository.countChildren(id) == 0
    }
}
