package top.vuhe.admin.spring.web.response

import java.io.Serializable

/**
 * ### 前端下拉树
 *
 * @param v 数据值字段名称
 * @param n 数据标题字段名称
 * @param s 子集数据字段名称
 * @author vuhe
 */
class ResultSelect(
    val v: String,
    val n: String,
    var s: List<ResultSelect>? = null
) : Serializable
