package top.vuhe.admin.api.exception

/**
 * ### 业务异常
 *
 * @param message 异常消息
 * @author vuhe
 */
class BusinessException(
    override val message: String
) : RuntimeException()
