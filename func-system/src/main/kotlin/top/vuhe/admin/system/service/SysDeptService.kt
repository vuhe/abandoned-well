package top.vuhe.admin.system.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.exception.businessRequire
import top.vuhe.admin.spring.database.service.TreeService
import top.vuhe.admin.system.domain.SysDept
import top.vuhe.admin.system.repository.SysDeptRepository
import top.vuhe.admin.system.repository.SysUserRepository

/**
 * 部门服务接口实现
 *
 * @author vuhe
 */
@Service
class SysDeptService(
    private val sysUserRepository: SysUserRepository,
    override val repository: SysDeptRepository
) : TreeService<SysDept>() {

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(ids: List<String>): Boolean {
        businessRequire(
            ids.find { it == "1" } == null
        ) { "不能删除顶级部门" }
        sysUserRepository.dropDept(ids)
        return super.remove(ids)
    }
}
