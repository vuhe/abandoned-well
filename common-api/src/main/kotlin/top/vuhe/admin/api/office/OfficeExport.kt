package top.vuhe.admin.api.office

import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.TemplateExportParams
import cn.afterturn.easypoi.word.WordExportUtil
import org.slf4j.LoggerFactory
import java.io.OutputStream

/**
 * ## 数据导入导出处理
 *
 * 支持以下功能：
 * - excel 的模版导出
 * - word 的模版导出
 *
 * @author vuhe
 */
object OfficeExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 通过模版导出 excel
     *
     * @param list        导出对象列表
     * @param templateUrl 模版 URL
     */
    fun <E : OfficeData> excel(list: List<E>, templateUrl: String): OfficeFile {
        val params = TemplateExportParams(templateUrl)
        val objects = list.map { it.data }
        val map = mutableMapOf("list" to objects) as MutableMap<String, *>
        val excel = ExcelExportUtil.exportExcel(params, map)
        return write { excel.write(it) }
    }

    /**
     * 通过模版导出 word
     *
     * @param list        导出对象列表
     * @param templateUrl 模版 URL
     */
    fun <E : OfficeData> word(list: List<E>, templateUrl: String): OfficeFile {
        val mapList = list.map { it.data }
        val word = WordExportUtil.exportWord07(templateUrl, mapList)
        return write { word.write(it) }
    }

    private inline fun write(crossinline block: (OutputStream) -> Unit) =
        OfficeFile {
            kotlin.runCatching { it.use(block) }
                .getOrElse { e -> log.error("导出失败！", e) }
        }
}
