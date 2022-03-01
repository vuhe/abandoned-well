package top.vuhe.admin.api.text

import cn.hutool.crypto.SecureUtil
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest

/**
 * sql注入处理工具类
 *
 * @author vuhe
 */
object SqlTextFilter {
    /**
     * sign 用于表字典加签的盐值【SQL漏洞】
     * （上线修改值 20200501，同步修改前端的盐值）
     */
    private const val TABLE_DICT_SIGN_SALT = "20200501"
    private const val XSS_STR =
        "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,"
    private val XSS_ARR = XSS_STR.split("\\|").toTypedArray()
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 针对表字典进行额外的sign签名校验（增加安全机制）
     */
    fun checkDictTableSign(dictCode: String, sign: String, request: HttpServletRequest) {
        //表字典SQL注入漏洞,签名校验
        val accessToken = request.getHeader("X-Access-Token")
        val signStr = dictCode + TABLE_DICT_SIGN_SALT + accessToken
        val javaSign = SecureUtil.md5().digestHex(signStr.toByteArray())
        if (javaSign != sign) {
            log.error("表字典，SQL注入漏洞签名校验失败 ：$sign!=$javaSign,dictCode=$dictCode")
            throw RuntimeException("无权限访问！")
        }
        log.info(" 表字典，SQL注入漏洞签名校验成功！sign=$sign,dictCode=$dictCode")
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param value sql语句
     */
    fun filterContent(value: String) {
        specialFilterContent(value, XSS_STR)
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param values sql语句
     */
    fun filterContent(values: List<String>) {
        values.forEach { value ->
            if (value.isNotEmpty()) {
                val lowercaseVal = value.localeLowercase()
                XSS_ARR.forEach { s ->
                    if (lowercaseVal.contains(s)) {
                        log.error("请注意，存在SQL注入关键词---> {}", s)
                        log.error("请注意，值可能存在SQL注入风险!---> {}", lowercaseVal)
                        throw RuntimeException("请注意，值可能存在SQL注入风险!--->$lowercaseVal")
                    }
                }
            }
        }
    }

    /**
     * @param value sql语句
     *
     * 特殊方法(不通用) 仅用于字典条件SQL参数，注入过滤
     */
    fun specialFilterContent(value: String) {
        val specialXssStr =
            " exec | insert | select | delete | update | drop | count | chr | mid | master | truncate | char | declare |;|+|"
        specialFilterContent(value, specialXssStr)
    }

    /**
     * @param value sql语句
     *
     * 特殊方法(不通用) 仅用于Online报表SQL解析，注入过滤
     */
    @Deprecated("")
    fun specialFilterContentForOnlineReport(value: String) {
        val specialXssStr = " exec | insert | delete | update | drop | chr | mid | master | truncate | char | declare |"
        specialFilterContent(value, specialXssStr)
    }

    private fun specialFilterContent(value: String, specialXssStr: String) {
        val xssArr = specialXssStr.split("\\|")
        specialFilterContent(value, xssArr)
    }

    private fun specialFilterContent(value: String, xssArr: List<String>) {
        if ("" == value) {
            return
        }
        // 统一转为小写
        val lowercaseVal = value.localeLowercase()
        xssArr.forEach { s ->
            if (lowercaseVal.contains(s) || lowercaseVal.startsWith(s.trim { it <= ' ' })) {
                log.error("请注意，存在SQL注入关键词---> {}", s)
                log.error("请注意，值可能存在SQL注入风险!---> {}", lowercaseVal)
                throw RuntimeException("请注意，值可能存在SQL注入风险!--->$lowercaseVal")
            }
        }
    }
}
