package top.vuhe.admin.spring.web.response

import top.vuhe.admin.spring.config.SystemConfiguration
import javax.servlet.http.HttpServletResponse

/**
 * 写出 json 对象
 *
 * @param json json 字符串
 */
private fun HttpServletResponse.writeJson(json: Any) {
    contentType = "application/json;charset=UTF-8"
    characterEncoding = "UTF-8"
    writer.write(SystemConfiguration.objectMapper.writeValueAsString(json))
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
