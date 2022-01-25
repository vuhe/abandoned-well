package top.vuhe.admin.spring.database.service.impl

import org.ktorm.database.Database
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.filter
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * ### 基本数据库服务
 * 提供增删改查的模版方法
 *
 * @author vuhe
 */
@Service
abstract class BaseService {
    @Autowired
    protected lateinit var database: Database

    /**
     * ### 条件过滤
     * 满足条件时执行过滤，否则不执行
     *
     * @param checker   过滤执行条件
     * @param predicate 过滤执行函数
     */
    protected fun <E : Any, T : Table<E>> EntitySequence<E, T>.filter(
        checker: Boolean,
        predicate: (T) -> ColumnDeclaring<Boolean>
    ): EntitySequence<E, T> {
        return if (checker) {
            filter(predicate)
        } else this
    }
}
