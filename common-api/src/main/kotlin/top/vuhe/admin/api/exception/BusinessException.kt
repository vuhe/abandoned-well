package top.vuhe.admin.api.exception

/**
 * ## 业务异常
 *
 * @param message 异常信息
 */
class BusinessException(override val message: String) : RuntimeException()
