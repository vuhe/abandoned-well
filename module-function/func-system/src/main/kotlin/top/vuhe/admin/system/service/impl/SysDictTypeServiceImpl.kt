package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.system.domain.SysDictType
import top.vuhe.admin.system.mapper.SysDictDataMapper
import top.vuhe.admin.system.mapper.SysDictTypeMapper
import top.vuhe.admin.system.service.ISysDictTypeService

/**
 * 字典类型服务实现类
 *
 * @author vuhe
 */
@Service
class SysDictTypeServiceImpl(
    private val sysDictTypeMapper: SysDictTypeMapper,
    private val sysDictDataMapper: SysDictDataMapper
) : CurdService<SysDictType>(sysDictTypeMapper), ISysDictTypeService {

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(id: String): Boolean {
        val type = sysDictTypeMapper.selectById(id)
            ?: return false
        sysDictDataMapper.deleteByCodes(listOf(type.typeCode))
        return super<CurdService>.remove(id)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        val list = sysDictTypeMapper.selectListByIds(ids).map { it.typeCode }
        sysDictDataMapper.deleteByCodes(list)
        return super.batchRemove(ids)
    }
}
