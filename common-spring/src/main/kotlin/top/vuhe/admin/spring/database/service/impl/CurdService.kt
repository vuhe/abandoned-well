package top.vuhe.admin.spring.database.service.impl

import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.entity.BaseEntity
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.spring.database.service.ICurdService

/**
 * 增删改查基本服务实现
 *
 * @author vuhe
 */
abstract class CurdService<E : BaseEntity>(private val mapper: CurdMapper<E>) : ICurdService<E> {
    override fun list(): List<E> = mapper.selectList()

    override fun list(param: E): List<E> = mapper.selectList(param)

    override fun getOneById(id: String): E? = mapper.selectById(id)

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: E): Boolean = mapper.insert(entity) > 0

    @Transactional(rollbackFor = [Exception::class])
    override fun modify(entity: E): Boolean = mapper.update(entity) > 0

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean = mapper.batchDelete(ids) > 0
}
