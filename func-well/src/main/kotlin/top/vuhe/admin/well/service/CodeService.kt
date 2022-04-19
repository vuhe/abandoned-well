package top.vuhe.admin.well.service

import org.springframework.stereotype.Service
import top.vuhe.admin.spring.database.service.CurdService
import top.vuhe.admin.well.domina.RegionCode
import top.vuhe.admin.well.repository.CodeRepository

/**
 * 地质分区代码服务类接口实现
 *
 * @author vuhe
 */
@Service
class CodeService(override val repository: CodeRepository) : CurdService<RegionCode>() {
    /**
     * 查询全部代码，并对选中代码标记
     */
    fun listWithChecked(codeId: String): List<RegionCode> {
        return list().onEach {
            // 是否选中，用于前端显示
            it["checked"] = it.id == codeId
        }
    }
}
