package top.vuhe.admin.api.office.handler

import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.TemplateExportParams
import cn.hutool.core.bean.BeanUtil
import org.apache.poi.ss.usermodel.Workbook
import org.slf4j.LoggerFactory
import java.io.OutputStream

/**
 * excel 模版导出
 *
 * @author vuhe
 */
internal object ExcelTemplateExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 通过模版导出
     */
    fun <T : Any> templateExport(list: List<T>, templateUrl: String): Workbook {
        val params = TemplateExportParams(templateUrl)
        val objects = list.map { objToMap(it) }
        val map = mutableMapOf("list" to objects) as MutableMap<String, *>
        return ExcelExportUtil.exportExcel(params, map)
    }

    /**
     * excel 写入
     */
    fun write(workbook: Workbook, output: OutputStream) {
        try {
            output.use { workbook.write(it) }
        } catch (e: Exception) {
            log.error("导出 excel 失败！", e)
        }
    }

    private fun objToMap(obj: Any): Map<String, Any> {
        return BeanUtil.beanToMap(obj)
    }
}
