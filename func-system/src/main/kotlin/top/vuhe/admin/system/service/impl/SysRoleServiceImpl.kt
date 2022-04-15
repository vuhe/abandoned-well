package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.repository.LinkRolePower
import top.vuhe.admin.system.repository.LinkUserRole
import top.vuhe.admin.system.repository.SysPowerRepository
import top.vuhe.admin.system.repository.SysRoleRepository
import top.vuhe.admin.system.service.ISysRoleService

/**
 * 角色服务实现类
 *
 * @author vuhe
 */
@Service
class SysRoleServiceImpl(
    private val linkUserRole: LinkUserRole,
    sysRoleMapper: SysRoleRepository,
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) : CurdService<SysRole>(sysRoleMapper), ISysRoleService {

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        // 删除关联表信息
        linkUserRole.deleteByRole(ids)
        linkRolePower.deleteByRole(ids)
        return super.batchRemove(ids)
    }

    override fun getRolePower(roleId: String): List<SysPower> {
        // 查询全部权限
        val allPower = sysPowerRepository.selectList()
        // 查询此角色的权限表
        val myPower = linkRolePower.selectPowerIdByRoleId(roleId)
        // 设置权限选中
        // 0-未选中，1-选中，2-半选
        allPower.forEach { sysPower ->
            sysPower["checkArr"] = if (sysPower.powerId in myPower) "1" else "0"
        }
        return allPower
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveRolePower(roleId: String, powerIds: List<String>): Boolean {
        return linkRolePower.insert(roleId, powerIds) > 0
    }
}
