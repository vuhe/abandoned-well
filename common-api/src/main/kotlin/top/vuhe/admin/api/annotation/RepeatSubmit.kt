package top.vuhe.admin.api.annotation

/**
 * ## 表单重复提交注解
 *
 * @author vuhe
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RepeatSubmit
