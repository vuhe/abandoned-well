package top.vuhe.admin.api.office

import top.vuhe.admin.api.network.ContentType
import top.vuhe.admin.api.network.setContent
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

/**
 * ### 数据导入导出处理
 *
 * 支持以下功能：
 * - excel 的模版导出
 * - word 的模版导出
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
    fun exportExcel(
        list: List<Any>, templateUrl: String,
        outName: String, response: HttpServletResponse
    ) {
        response.setContent(ContentType.FILE_XLS)
        response.filename = "${outName.urlCode()}.xls"
        TemplateExport.excel(list, templateUrl).writeTo(response.outputStream)
    }

    /**
     * 通过模版导出 word
     *
     * @param list        导出对象列表
     * @param templateUrl 模版 URL
     * @param outName     输出名称
     * @param response    输出的响应
     */
    fun exportWord(
        list: List<Any>, templateUrl: String,
        outName: String, response: HttpServletResponse
    ) {
        response.setContent(ContentType.FILE_DOCX)
        response.filename = "${outName.urlCode()}.docx"
        TemplateExport.word(list, templateUrl).writeTo(response.outputStream)
    }

    private fun String.urlCode(): String {
        // 这里URLEncoder.encode可以防止中文乱码
        return URLEncoder.encode(this, "UTF-8").replace("\\+", "%20")
    }

    private var HttpServletResponse.filename: String
        get() = throw UnsupportedOperationException("")
        set(value) = setHeader("Content-disposition", "attachment;filename*=utf-8''$value")

}
