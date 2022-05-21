package top.vuhe.domain.role

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.database.service.CurdService
import top.vuhe.domain.power.SysPower
import top.vuhe.domain.power.SysPowerRepository
import top.vuhe.mapper.LinkRolePower
import top.vuhe.mapper.LinkUserRole

/**
 * 角色服务实现类
 *
 * @author vuhe
 */
@Service
class SysRoleService(
    private val linkUserRole: LinkUserRole,
    override val repository: SysRoleRepository,
    private val linkRolePower: LinkRolePower,
    private val sysPowerRepository: SysPowerRepository
) : CurdService<SysRole>() {

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(ids: List<String>): Boolean {
        linkUserRole.deleteByRole(ids)
        linkRolePower.deleteByRole(ids)
        return super.remove(ids)
    }

    /** 获取角色权限 */
    fun getRolePower(roleId: String): List<SysPower> {
        // 查询全部权限
        val allPower = sysPowerRepository.selectList()
        // 查询此角色的权限表
        val myPower = linkRolePower.selectPowerIdByRoleId(roleId)
        // 设置权限选中
        // 0-未选中，1-选中，2-半选
        allPower.forEach {
            it.checkArr = if (it.powerId in myPower) "1" else "0"
        }
        return allPower
    }

    /** 保存角色权限 */
    @Transactional(rollbackFor = [Exception::class])
    fun saveRolePower(roleId: String, powerIds: List<String>): Boolean {
        return linkRolePower.insert(roleId, powerIds) > 0
    }
}
