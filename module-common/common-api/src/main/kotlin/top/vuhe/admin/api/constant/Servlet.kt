package top.vuhe.admin.api.constant

const val JSON_UTF8 = "application/json;charset=UTF-8"
const val REMOTE_HOST_0 = "0:0:0:0:0:0:0:1"
const val REMOTE_HOST_127 = "127.0.0.1"

object Browser {
    const val FIRE_FOX_UA = "Firefox"
    const val FIRE_FOX_NAME = "火狐浏览器"
    const val CHROME_UA = "Chrome"
    const val CHROME_NAME = "谷歌浏览器"
    const val IE_UA = "Trident"
    const val IE_NAME = "IE浏览器"
    const val SAFARI_UA = "Safari"
    const val SAFARI_NAME = "Safari浏览器"
    const val UNKNOWN = "你用啥浏览器"
}

object Header {
    const val X_REQUESTED_WITH = "X-Requested-With"
    const val XML_HTTP_REQUEST = "XMLHttpRequest"
    const val CONTENT_TYPE = "Content-type"
    const val UA = "User-Agent"
}

object SystemType {
    const val WIN_UA = "windows"
    const val WIN_NAME = "Windows"
    const val MAC_UA = "mac"
    const val MAC_NAME = "Mac"
    const val UNIX_UA = "x11"
    const val UNIX_NAME = "Unix"
    const val ANDROID_UA = "android"
    const val ANDROID_NAME = "Android"
    const val IPHONE_UA = "iphone"
    const val IPHONE_NAME = "iPhone"
    const val UNKNOWN = "UnKnown, More-Info: "
}
