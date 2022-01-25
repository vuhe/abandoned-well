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
sealed class ResultObj<out T> private constructor(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: T?,
) : Serializable {

    class Success<out T>(code: Int = 200, message: String = "操作成功", data: T? = null) :
        ResultObj<T>(code = code, msg = message, data = data, success = true)

    class Fail<out T>(code: Int = 500, message: String = "操作失败", data: T? = null) :
        ResultObj<T>(code = code, msg = message, data = data, success = false)

}
