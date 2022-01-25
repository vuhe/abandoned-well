package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysNotice
import top.vuhe.admin.system.mapper.SysNoticeMapper
import top.vuhe.admin.system.mapper.SysUserMapper
import top.vuhe.admin.system.service.ISysNoticeService

/**
 * 通知服务实现类
 *
 * @author vuhe
 */
@Service
class SysNoticeServiceImpl(
    sysNoticeMapper: SysNoticeMapper,
    private val sysUserMapper: SysUserMapper
) : CurdService<SysNotice>(sysNoticeMapper), ISysNoticeService {
    override fun page(param: SysNotice, pageDomain: PageDomain): TablePage<SysNotice> {
        val page = TablePage(list(param), pageDomain)
        // 设置用户名
        page.list.forEach {
            it.senderName = sysUserMapper.selectById(it.sender)
                ?.realName ?: ""
            if (it.accept.isNotEmpty()) {
                it.acceptName = sysUserMapper.selectById(it.accept)
                    ?.realName ?: ""
            }
        }
        return page
    }
}
