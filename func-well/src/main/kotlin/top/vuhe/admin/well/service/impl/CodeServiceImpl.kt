package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.RegionCode
import top.vuhe.admin.well.repository.CodeRepository
import top.vuhe.admin.well.service.ICodeService

/**
 * 地质分区代码服务类接口实现
 *
 * @author vuhe
 */
@Service
class CodeServiceImpl(
    codeRepository: CodeRepository
) : CurdService<RegionCode>(codeRepository), ICodeService {
    override fun listWithChecked(codeId: String): List<RegionCode> {
        return list().onEach {
            // 是否选中，用于前端显示
            it["checked"] = it.id == codeId
        }
    }
}
