package top.vuhe.admin.api.network

@Suppress("SpellCheckingInspection")
enum class ContentType(val value: String) {
    FILE_DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    FILE_XLS("application/vnd.ms-excel"),
    TEXT_JSON("application/json;charset=UTF-8")
}
