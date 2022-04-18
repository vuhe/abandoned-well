package top.vuhe.admin.spring.web.response

import com.fasterxml.jackson.databind.ObjectMapper
import top.vuhe.admin.api.constant.spring
import top.vuhe.admin.api.network.ContentType
import top.vuhe.admin.api.network.setContent
import javax.servlet.http.HttpServletResponse

private val objectMapper: ObjectMapper by spring()

/**
 * 写出 json 对象
 *
 * @param json json 字符串
 */
private fun HttpServletResponse.writeJson(json: Any) {
    setContent(ContentType.TEXT_JSON)
    writer.write(objectMapper.writeValueAsString(json))
}

/**
 * http 成功响应
 */
fun HttpServletResponse.success(message: String = "操作成功", data: Any? = null) {
    val result = ResultObj.Success(message, data)
    writeJson(result)
}

/**
 * http 失败响应
 */
fun HttpServletResponse.fail(code: Int = 500, message: String = "操作失败") {
    val result = ResultObj.Fail(code, message)
    writeJson(result)
}
