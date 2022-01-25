package top.vuhe.admin.spring.property

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 系统权限配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("project.security")
object SecurityProperty {
    /**
     * 超级管理员不认证
     */
    var superAuthOpen = false

    /**
     * 不验证权限角色名
     */
    var superRole: String? = null

    /**
     * 记住密码标识
     */
    var rememberKey: String? = null

    /**
     * 开放接口列表
     */
    var openApi: Array<String> = emptyArray()

    /**
     * 是否允许多账号在线
     */
    var maximum = 1
}
