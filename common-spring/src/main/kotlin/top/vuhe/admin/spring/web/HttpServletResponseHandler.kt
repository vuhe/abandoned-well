package top.vuhe.admin.spring.web

import com.fasterxml.jackson.databind.ObjectMapper
import top.vuhe.admin.api.network.ContentType
import top.vuhe.admin.api.network.setContent
import top.vuhe.admin.spring.web.response.AjaxCode
import top.vuhe.admin.spring.web.response.AjaxResult
import javax.servlet.http.HttpServletResponse

abstract class HttpServletResponseHandler(private val objectMapper: ObjectMapper) {
    private fun HttpServletResponse.writeJson(json: Any) {
        setContent(ContentType.TEXT_JSON)
        writer.write(objectMapper.writeValueAsString(json))
    }

    /** http 成功响应 */
    protected fun HttpServletResponse.success(message: String = "操作成功", data: Any? = null) {
        val result = AjaxResult.success(message, data)
        writeJson(result)
    }

    /** http 失败响应 */
    protected fun HttpServletResponse.fail(
        code: AjaxCode = AjaxCode.HandleException, message: String = "操作失败"
    ) {
        val result = AjaxResult.fail(code, message)
        writeJson(result)
    }
}
