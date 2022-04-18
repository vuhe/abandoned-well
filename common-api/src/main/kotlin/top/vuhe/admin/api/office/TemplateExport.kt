package top.vuhe.admin.api.office

import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.TemplateExportParams
import cn.afterturn.easypoi.word.WordExportUtil
import cn.hutool.core.bean.BeanUtil
import org.slf4j.LoggerFactory
import java.io.OutputStream

internal object TemplateExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun excel(list: List<Any>, templateUrl: String): OfficeFileData {
        val params = TemplateExportParams(templateUrl)
        val objects = list.map { BeanUtil.beanToMap(it) }
        val map = mutableMapOf("list" to objects) as MutableMap<String, *>
        val excel = ExcelExportUtil.exportExcel(params, map)
        return write { excel.write(it) }
    }

    fun word(list: List<Any>, templateUrl: String): OfficeFileData {
        val mapList = list.map { BeanUtil.beanToMap(it) }
        val word = WordExportUtil.exportWord07(templateUrl, mapList)
        return write { word.write(it) }
    }

    private inline fun write(crossinline block: (OutputStream) -> Unit) =
        OfficeFileData {
            kotlin.runCatching { it.use(block) }
                .getOrElse { e -> log.error("导出失败！", e) }
        }
}
