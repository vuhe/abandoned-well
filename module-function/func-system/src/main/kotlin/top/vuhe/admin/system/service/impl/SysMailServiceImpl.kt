package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.vuhe.admin.api.mail.MailService
import top.vuhe.admin.system.domain.SysMail
import top.vuhe.admin.system.mapper.SysMailMapper
import top.vuhe.admin.system.service.ISysMailService

/**
 * 邮件服务接口实现
 *
 * @author vuhe
 */
@Service
class SysMailServiceImpl(
    private val sysMailMapper: SysMailMapper
) : ISysMailService {
    override val enable: Boolean get() = MailService.enable

    @Transactional(rollbackFor = [Exception::class])
    override fun save(sysMail: SysMail): Boolean {
        return if (sendMail(sysMail)) {
            sysMailMapper.insert(sysMail) > 0
        } else false
    }

    override fun list(param: SysMail): List<SysMail> {
        return sysMailMapper.selectList(param)
    }

    override fun sendMail(sysMail: SysMail): Boolean {
        val tos = sysMail.receiver.split(";")
        return MailService.send(tos, sysMail.subject, sysMail.content, false)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchRemove(ids: List<String>): Boolean {
        return sysMailMapper.batchDelete(ids) > 0
    }
}
