package top.vuhe.admin.api.office

import org.springframework.web.multipart.MultipartFile
import top.vuhe.admin.api.office.handler.CsvDataHandler
import top.vuhe.admin.api.office.handler.ExcelTemplateExport
import top.vuhe.admin.api.office.handler.WordTemplateExport
import java.io.ByteArrayInputStream
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

/**
 * ### 数据导入导出处理
 * 支持以下功能：
 * - excel 的模版导出
 * - csv 的导入导出
 *
 * 由于 excel, word 的格式复杂，暂不支持 excel, word 的导入和导出
 *
 * @author vuhe
 */
class OfficeHandler {

    /**
     * 通过模版导出 excel
     *
     * @param list        导出对象列表
     * @param templateUrl 模版 URL
     * @param outName     输出名称
     * @param response    输出的响应
     */
    fun <T : Any> exportExcel(
        list: List<T>, templateUrl: String,
        outName: String, response: HttpServletResponse
    ) {
        val workbook = ExcelTemplateExport.templateExport(list, templateUrl)
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        // 这里URLEncoder.encode可以防止中文乱码
        val fileName = URLEncoder.encode(outName, "UTF-8").replace("\\+", "%20")
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''$fileName.xls")
        ExcelTemplateExport.write(workbook, response.outputStream)
    }

    /**
     * 通过模版导出 word
     *
     * @param list        导出对象列表
     * @param templateUrl 模版 URL
     * @param outName     输出名称
     * @param response    输出的响应
     */
    fun <T : Any> exportWord(
        list: List<T>, templateUrl: String,
        outName: String, response: HttpServletResponse
    ) {
        val doc = WordTemplateExport.templateWord(list, templateUrl)
        response.contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        response.characterEncoding = "utf-8"
        // 这里URLEncoder.encode可以防止中文乱码
        val fileName = URLEncoder.encode(outName, "UTF-8").replace("\\+", "%20")
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''$fileName.docx")
        WordTemplateExport.write(doc, response.outputStream)
    }

    /**
     * 导出 CSV
     *
     * @param list     导出对象列表
     * @param outName  输出名称
     * @param response 输出的响应
     */
    inline fun <reified T : Any> exportCsv(list: List<T>, outName: String, response: HttpServletResponse) =
        exportCsv(T::class, list, outName, response)

    /**
     * 导出 CSV
     *
     * @param clazz    导出对象 class
     * @param list     导出对象列表
     * @param outName  输出名称
     * @param response 输出的响应
     */
    fun <T : Any> exportCsv(clazz: KClass<T>, list: List<T>, outName: String, response: HttpServletResponse) {
        response.contentType = "text/csv"
        response.characterEncoding = "utf-8"
        // 这里URLEncoder.encode可以防止中文乱码
        val fileName = URLEncoder.encode(outName, "UTF-8").replace("\\+", "%20")
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''$fileName.docx")
        CsvDataHandler.export(clazz, list, response.outputStream)
    }

    /**
     * 导入 CSV
     *
     * @param file 解析文件
     * @return 解析后的列表
     */
    inline fun <reified T : Any> import(file: MultipartFile): List<T> =
        import(T::class, file)

    /**
     * 导入 CSV
     *
     * @param clazz 导入对象 class
     * @param file  解析文件
     * @return 解析后的列表
     */
    fun <T : Any> import(clazz: KClass<T>, file: MultipartFile): List<T> {
        val import = ByteArrayInputStream(file.bytes)
        return CsvDataHandler.import(clazz, import)
    }
}
