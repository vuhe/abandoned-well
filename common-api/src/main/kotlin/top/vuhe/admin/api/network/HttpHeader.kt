package top.vuhe.admin.api.network

enum class HttpHeader(val tag: String) {
    UserAgent("User-Agent"),
    XRequestedWith("X-Requested-With"),
    ContentDisposition("Content-disposition")
}
