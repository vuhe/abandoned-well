package top.vuhe.admin.spring.database.service

import org.ktorm.entity.Entity
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.spring.database.repository.CurdRepository
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.request.PageParam

/**
 * 增删改查基本服务实现
 *
 * @author vuhe
 */
abstract class CurdService<E : Entity<E>>(private val mapper: CurdRepository<*, E>) : ICurdService<E> {
    override fun list(): List<E> = mapper.selectList()

    override fun page(param: PageParam): TablePage<E> =
        mapper.selectPage(param)

    override fun list(param: PageParam): List<E> = mapper.selectList(param)

    override fun getOneById(id: String): E? = mapper.selectById(id)

    @Transactional(rollbackFor = [Exception::class])
    override fun add(entity: E): Boolean = mapper.insert(entity) > 0

    @Transactional(rollbackFor = [Exception::class])
    override fun modify(entity: E): Boolean = mapper.update(entity) > 0

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean = mapper.delete(ids) > 0
}
