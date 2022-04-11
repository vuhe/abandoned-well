package top.vuhe.admin.spring.web.response

import java.io.Serializable

/**
 * Ajax 返回 JSON 结果封装数据
 *
 * @param success 是否成功
 * @param code 状态码
 * @param msg 提示消息
 * @param data 返回数据
 * @author vuhe
 */
sealed class ResultObj private constructor(
    val success: Boolean, val code: Int,
    val msg: String, val data: Any?,
) : Serializable {

    class Success internal constructor(message: String = "操作成功", data: Any? = null) :
        ResultObj(code = 200, msg = message, data = data, success = true)

    class Fail internal constructor(code: Int = 500, message: String = "操作失败") :
        ResultObj(code = code, msg = message, data = null, success = false)

}
