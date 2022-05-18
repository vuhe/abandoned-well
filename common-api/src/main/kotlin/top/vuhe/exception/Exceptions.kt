package top.vuhe.exception

/**
 * 业务要求非 null 值
 *
 * @author vuhe
 */
inline fun <T : Any> businessNotNull(value: T?, message: () -> Any): T {
    return value ?: throw BusinessException(message().toString())
}

/**
 * 业务要求 true 值
 *
 * @author vuhe
 */
inline fun businessRequire(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        throw BusinessException(lazyMessage().toString())
    }
}
