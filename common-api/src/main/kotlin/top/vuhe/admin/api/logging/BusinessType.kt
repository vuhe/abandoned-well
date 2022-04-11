package top.vuhe.admin.api.logging

import com.fasterxml.jackson.annotation.JsonValue

/**
 * ### 日志业务类型
 * 前端实现时使用 definition 字段
 *
 * @author vuhe
 */
enum class BusinessType(@JsonValue val definition: String) {
    ADD("新增"),
    EDIT("修改"),
    REMOVE("删除"),
    QUERY("查询"),
    EXPORT("导出"),
    OTHER("其他")
}
