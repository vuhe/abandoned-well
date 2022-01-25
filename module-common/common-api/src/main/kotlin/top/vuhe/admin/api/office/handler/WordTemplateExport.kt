package top.vuhe.admin.api.office.handler

import cn.afterturn.easypoi.word.WordExportUtil
import cn.hutool.core.bean.BeanUtil
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.slf4j.LoggerFactory
import java.io.OutputStream

/**
 * word 模版导出
 *
 * @author vuhe
 */
internal object WordTemplateExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 通过模版导出
     */
    fun <T : Any> templateWord(list: List<T>, templateUrl: String): XWPFDocument {
        val mapList = list.map { objToMap(it) }
        return WordExportUtil.exportWord07(templateUrl, mapList)
    }

    /**
     * word 写入
     */
    fun write(document: XWPFDocument, output: OutputStream) {
        try {
            output.use { document.write(it) }
        } catch (e: Exception) {
            log.error("导出 word 失败！", e)
        }
    }

    private fun objToMap(obj: Any): Map<String, Any> {
        return BeanUtil.beanToMap(obj)
    }
}
