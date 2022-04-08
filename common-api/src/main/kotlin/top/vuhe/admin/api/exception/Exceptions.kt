package top.vuhe.admin.api.exception

/**
 * 业务要求非 null 值
 */
fun <T : Any> businessNotNull(value: T?, message: () -> String = { "系统要求不能为 null!" }): T {
    return value ?: throw BusinessException(message())
}
