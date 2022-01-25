package top.vuhe.admin.api.office.annotation

import top.vuhe.admin.api.office.enums.HandleMode

/**
 * 数据处理注解
 *
 * @param value  对应导出的名称(暂不支持)
 * @param ignore 是否忽略
 * @param mode   导出模式
 * @author vuhe
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class OfficeProperty(
    val value: String,
    val ignore: Boolean = false,
    val mode: HandleMode = HandleMode.ALL
)
