package top.vuhe.domain.dept

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.database.service.TreeService
import top.vuhe.domain.user.SysUserRepository
import top.vuhe.exception.businessRequire

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
