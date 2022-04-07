package top.vuhe.admin.spring.web.response

import java.io.Serializable

/**
 * ### 前端 tree 结果封装数据
 *
 * @param data 返回数据
 * @author vuhe
 */
class ResultTree(
    val data: Any
) : Serializable {
    /**
     * 状态信息
     */
    val status = object {
        val code = 200
        val message = "默认"
    }
}
