package top.vuhe.annotation

/**
 * ## 忽略 xss 注解
 *
 * @author vuhe
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class XssCleanIgnore
