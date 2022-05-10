package top.vuhe.admin.api.extra

import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Entities
import org.jsoup.safety.Safelist

internal fun xssClean(text: String): String {
    val str = text.trim()

    // 1. 为空直接返回
    str.ifEmpty { return str }

    // jsoup html 清理
    val outputSettings = Document.OutputSettings()
        // 2. 转义，没找到关闭的方法，目前这个规则最少
        .escapeMode(Entities.EscapeMode.xhtml)
        // 3. 保留换行
        .prettyPrint(false)

    // 4. 清理后的 html
    val escapedHtml = Jsoup.clean(str, "", WhiteList, outputSettings)

    // 5. 反转义
    return Entities.unescape(escapedHtml)
}

/**
 * 做自己的白名单，允许base64的图片通过等
 *
 * @author michael
 * @author vuhe
 */
@Suppress("SpellCheckingInspection")
private object WhiteList : Safelist() {
    init {
        addTags(
            "a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup",
            "dd", "div", "span", "embed", "object", "dl", "dt", "em", "h1", "h2", "h3",
            "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "strike",
            "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead",
            "tr", "u", "ul"
        )
        addAttributes("a", "href", "title", "target")
        addAttributes("blockquote", "cite")
        addAttributes("col", "span")
        addAttributes("colgroup", "span")
        addAttributes("img", "align", "alt", "src", "title")
        addAttributes("ol", "start")
        addAttributes("q", "cite")
        addAttributes("table", "summary")
        addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
        addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width")
        addAttributes("video", "src", "autoplay", "controls", "loop", "muted", "poster", "preload")
        addAttributes("object", "width", "height", "classid", "codebase")
        addAttributes("param", "name", "value")
        addAttributes(
            "embed",
            "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess",
            "flashvars", "name", "type", "pluginspage"
        )
        addAttributes(":all", "class", "style", "height", "width", "type", "id", "name")
        addProtocols("blockquote", "cite", "http", "https")
        addProtocols("cite", "cite", "http", "https")
        addProtocols("q", "cite", "http", "https")

        //如果添加以下的协议，那么href 必须是http、 https 等开头，相对路径则被过滤掉了
        //addProtocols("a", "href", "ftp", "http", "https", "mailto", "tel");

        //如果添加以下的协议，那么src必须是http 或者 https 开头，相对路径则被过滤掉了，
        //所以必须注释掉，允许相对路径的图片资源
        //addProtocols("img", "src", "http", "https");
    }

    override fun isSafeAttribute(tagName: String, el: Element, attr: Attribute): Boolean {
        // 不允许 javascript 开头的 src 和 href
        if ("src".equals(attr.key, ignoreCase = true) || "href".equals(attr.key, ignoreCase = true)) {
            val value = attr.value
            if (value.isNotBlank() && value.startsWith("javascript", ignoreCase = true)) {
                return false
            }
        }
        // 允许 base64 的图片内容
        return if ("img" == tagName && "src" == attr.key && attr.value.startsWith("data:;base64")) {
            true
        } else super.isSafeAttribute(tagName, el, attr)
    }
}
