package top.vuhe.annotation

import top.vuhe.logging.BusinessType

/**
 * ## 系统日志注解
 *
 * @param value    标题 默认无参输入
 * @param describe Describe 默认输入
 * @param type     业务类型 默认Query
 * @author vuhe
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Logging(
    val value: String = "暂无标题",
    val describe: String = "暂无介绍",
    val type: BusinessType = BusinessType.QUERY
)
