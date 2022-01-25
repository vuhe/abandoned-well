package top.vuhe.admin.system.service

import top.vuhe.admin.spring.database.service.ICurdService
import top.vuhe.admin.system.domain.SysMail

/**
 * 邮件服务接口类
 *
 * @author vuhe
 */
interface ISysMailService : ICurdService<SysMail> {
    /**
     * 是否启用邮件服务
     */
    val enable: Boolean


    /**
     * 邮件保存
     */
    fun save(sysMail: SysMail): Boolean

    /**
     * 发送邮件
     */
    fun sendMail(sysMail: SysMail): Boolean

    /**
     * 此接口不支持
     */
    @Deprecated("UnsupportedOperation")
    override fun getOneById(id: String): SysMail? {
        throw UnsupportedOperationException()
    }

    /**
     * 此接口不支持
     */
    @Deprecated("UnsupportedOperation")
    override fun add(entity: SysMail): Boolean {
        throw UnsupportedOperationException()
    }

    /**
     * 此接口不支持
     */
    @Deprecated("UnsupportedOperation")
    override fun modify(entity: SysMail): Boolean {
        throw UnsupportedOperationException()
    }
}
