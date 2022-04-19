package top.vuhe.admin.spring.web.response

enum class AjaxCode(val code: Int) {
    Success(200),
    AccessDenied(403),
    HandleException(500)
}
