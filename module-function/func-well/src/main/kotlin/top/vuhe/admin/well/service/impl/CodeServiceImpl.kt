package top.vuhe.admin.well.service.impl

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.impl.CurdService
import top.vuhe.admin.well.domina.RegionCode
import top.vuhe.admin.well.mapper.CodeMapper
import top.vuhe.admin.well.service.ICodeService

/**
 * 地质分区代码服务类接口实现
 *
 * @author vuhe
 */
@Service
class CodeServiceImpl(
    private val codeMapper: CodeMapper
) : CurdService<RegionCode>(codeMapper), ICodeService {
    private val emptyParam = RegionCode()

    override fun listWithChecked(codeId: String): List<RegionCode> {
        val list = list(emptyParam)
        val code = list.find { it.id == codeId }
            ?: return list
        code.checked = true
        return list
    }
}
