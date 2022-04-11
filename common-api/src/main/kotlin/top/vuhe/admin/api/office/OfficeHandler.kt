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
    private const val utf8 = "UTF-8"
    private const val excelType = "application/vnd.ms-excel"

    @Suppress("SpellCheckingInspection")
    private const val wordType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"

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
        response.contentType = excelType
        response.characterEncoding = utf8
        response.filename = "${outName.urlCode()}.xls"
        TemplateExcelExport.templateExport(list, templateUrl).writeTo(response.outputStream)
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
        response.contentType = wordType
        response.characterEncoding = utf8
        response.filename = "${outName.urlCode()}.docx"
        TemplateWordExport.templateWord(list, templateUrl).writeTo(response.outputStream)
    }

    private fun String.urlCode(): String {
        // 这里URLEncoder.encode可以防止中文乱码
        return URLEncoder.encode(this, utf8).replace("\\+", "%20")
    }

    private var HttpServletResponse.filename: String
        get() = throw UnsupportedOperationException("")
        set(value) = setHeader("Content-disposition", "attachment;filename*=utf-8''$value")

}
