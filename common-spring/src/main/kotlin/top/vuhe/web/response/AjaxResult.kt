package top.vuhe.web.response

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
data class AjaxResult private constructor(
    val success: Boolean, val code: Int, val msg: String, val data: Any?,
) : Serializable {

    companion object {
        fun success(message: String = "操作成功", data: Any? = null) =
            AjaxResult(code = AjaxCode.Success.code, msg = message, data = data, success = true)

        fun fail(type: AjaxCode = AjaxCode.HandleException, message: String = "操作失败") =
            AjaxResult(code = type.code, msg = message, data = null, success = false)

    }

}
