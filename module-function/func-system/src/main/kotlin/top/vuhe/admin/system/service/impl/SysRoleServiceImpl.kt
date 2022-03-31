package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysPower
import top.vuhe.admin.system.domain.SysRole
import top.vuhe.admin.system.mapper.SysPowerMapper
import top.vuhe.admin.system.mapper.SysRoleMapper
import top.vuhe.admin.system.service.ISysRoleService

/**
 * 角色服务实现类
 *
 * @author vuhe
 */
@Service
class SysRoleServiceImpl : CurdService<SysRole>(SysRoleMapper), ISysRoleService {
    private val sysRoleMapper = SysRoleMapper
    private val sysPowerMapper = SysPowerMapper

    override fun getRolePower(roleId: String): List<SysPower> {
        // 查询全部权限
        val allPower = sysPowerMapper.selectList()
        // 查询此角色的权限表
        val myPower = sysRoleMapper.selectPowerIdByRoleId(roleId)
        // 设置权限选中
        allPower.forEach { sysPower ->
            if (sysPower.powerId in myPower) {
                sysPower.checkArr = "1"
            }
        }
        return allPower
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveRolePower(roleId: String, powerIds: List<String>): Boolean {
        return sysRoleMapper.batchInsertRolePower(roleId, powerIds) > 0
    }
}
