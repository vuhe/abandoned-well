package top.vuhe.admin.api.network

import javax.servlet.http.HttpServletResponse

private const val charset = "UTF-8"

internal operator fun HttpServletResponse.set(header: HttpHeader, value: String) {
    setHeader(header.tag, value)
}

fun HttpServletResponse.setContent(type: ContentType) {
    contentType = type.value
    characterEncoding = charset
}
