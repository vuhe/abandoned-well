package top.vuhe.admin.api.network

import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/** 判断是否为 Ajax 请求 */
val HttpServletRequest.isAjax: Boolean
    get() = "XMLHttpRequest" == getHeader("X-Requested-With")

/** 获取 UserAgent */
val HttpServletRequest.userAgent: HttpUserAgent
    get() = HttpUserAgent(getHeader("User-Agent"))

/** 设置 ContentType 和 characterEncoding */
fun HttpServletResponse.setContent(type: ContentType) {
    contentType = type.value
    characterEncoding = "UTF-8"
}

/** 设置 filename, 更改 Content-disposition */
fun HttpServletResponse.setFilename(name: String) {
    // 这里URLEncoder.encode可以防止中文乱码
    val encoded = URLEncoder.encode(name, "UTF-8").replace("\\+", "%20")
    setHeader("Content-disposition", "attachment;filename*=utf-8''$encoded")
}
