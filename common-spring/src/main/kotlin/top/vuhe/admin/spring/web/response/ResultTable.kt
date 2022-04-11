package top.vuhe.admin.spring.web.response

import java.io.Serializable

/**
 * ### 前端表格数据封装
 *
 * @param data 数据对象
 * @param count 消息总量
 * @author vuhe
 */
class ResultTable(val data: List<*>, val count: Long? = null) : Serializable {
    val code: Int = 0
    val msg: String = ""
}
