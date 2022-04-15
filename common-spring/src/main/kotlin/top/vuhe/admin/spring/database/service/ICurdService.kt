package top.vuhe.admin.spring.database.service

import org.ktorm.entity.Entity
import top.vuhe.admin.spring.database.table.TablePage
import top.vuhe.admin.spring.web.request.PageParam

/**
 * 包含基本增删改查的服务接口
 * > 如果不需要某个接口，可以在继承中标记为废弃，抛出异常，
 * 同时，如果有过多接口不需要，建议不要继承，直接写接口
 *
 * @author vuhe
 */
interface ICurdService<E : Entity<E>> {
    /**
     * 查询全部列表数据
     */
    fun list(): List<E>

    /**
     * 根据条件查询列表数据
     */
    fun list(param: PageParam): List<E>

    /**
     * 根据条件查询分页数据
     */
    fun page(param: PageParam): TablePage<E>

    /**
     * 根据 id 获取一个实体
     */
    fun getOneById(id: String): E?

    /**
     * 添加实体
     */
    fun add(entity: E): Boolean

    /**
     * 通过 id 修改，id 错误不会修改任何值
     */
    fun modify(entity: E): Boolean

    /**
     * 通过 id 删除
     */
    fun remove(id: String): Boolean = batchRemove(listOf(id))

    /**
     * 通过 id 批量删除
     */
    fun batchRemove(ids: List<String>): Boolean
}
