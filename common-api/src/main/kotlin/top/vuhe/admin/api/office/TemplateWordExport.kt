package top.vuhe.admin.api.office

import cn.afterturn.easypoi.word.WordExportUtil
import cn.hutool.core.bean.BeanUtil
import org.slf4j.LoggerFactory

/**
 * word 模版导出
 *
 * @author vuhe
 */
internal object TemplateWordExport {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 通过模版导出
     */
    fun <T : Any> templateWord(list: List<T>, templateUrl: String): OfficeFileData {
        val mapList = list.map { BeanUtil.beanToMap(it) }
        val word = WordExportUtil.exportWord07(templateUrl, mapList)
        return OfficeFileData { output ->
            kotlin.runCatching {
                output.use { word.write(it) }
            }.getOrElse { log.error("导出 word 失败！", it) }
        }
    }
}
