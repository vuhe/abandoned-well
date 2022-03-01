package top.vuhe.admin.api.office

import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

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
object OfficeHandler {

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

}
