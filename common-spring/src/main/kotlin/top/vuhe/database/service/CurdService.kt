package top.vuhe.database.service

import org.ktorm.entity.Entity
import org.springframework.transaction.annotation.Transactional
import top.vuhe.database.repository.CurdRepository
import top.vuhe.database.table.TablePage
import top.vuhe.web.request.PageParam

/**
 * 增删改查基本服务实现
 *
 * @author vuhe
 */
abstract class CurdService<E : Entity<E>> {
    protected abstract val repository: CurdRepository<*, E>

    fun list(): List<E> = repository.selectList()

    fun page(param: PageParam): TablePage<E> = repository.selectPage(param)

    open fun list(param: PageParam): List<E> = repository.selectList(param)

    fun getOneById(id: String): E? = repository.selectById(id)

    @Transactional(rollbackFor = [Exception::class])
    open fun add(entity: E): Boolean = repository.insert(entity) > 0

    @Transactional(rollbackFor = [Exception::class])
    open fun modify(entity: E): Boolean = repository.update(entity) > 0

    fun remove(id: String) = remove(listOf(id))

    @Transactional(rollbackFor = [Exception::class])
    open fun remove(ids: List<String>): Boolean = repository.delete(ids) > 0
}
