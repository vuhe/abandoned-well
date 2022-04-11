package top.vuhe.admin.api.office

import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.TemplateExportParams
import cn.hutool.core.bean.BeanUtil
import org.slf4j.LoggerFactory

/**
 * excel 模版导出
 *
 * @author vuhe
 */
internal object TemplateExcelExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 通过模版导出
     */
    fun <T : Any> templateExport(list: List<T>, templateUrl: String): OfficeFileData {
        val params = TemplateExportParams(templateUrl)
        val objects = list.map { BeanUtil.beanToMap(it) }
        val map = mutableMapOf("list" to objects) as MutableMap<String, *>
        val excel = ExcelExportUtil.exportExcel(params, map)
        return OfficeFileData { output ->
            kotlin.runCatching {
                output.use { excel.write(it) }
            }.getOrElse { log.error("导出 excel 失败！", it) }
        }
    }
}
