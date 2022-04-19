package top.vuhe.admin.spring.web.response

import java.io.Serializable

/**
 * ## 前端 d-tree 结果封装数据
 *
 * @param data 返回数据
 * @author vuhe
 */
data class LayuiTree(
    val data: List<*>
) : Serializable {
    val status = DTreeStatus

    object DTreeStatus {
        const val code = 200
        const val message = "默认"
    }
}
