package top.vuhe.admin.spring.property

import io.swagger.v3.oas.models.info.Contact
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * ### 接口文档配置类
 *
 * @author vuhe
 */
@Suppress("SpellCheckingInspection")
@ConfigurationProperties("springdoc.swagger")
object SwaggerProperty {
    /** 系 统 标 题  */
    var title: String = "井管理系统 API"

    /** 描 述 信 息  */
    var describe: String = "废弃井管理系统"

    /** 版 本 信 息  */
    var version: String = "Release 1.0.0"

    /** 扩 展 信 息  */
    var contact= Contact().apply {
        name = "vuhe"
        url = "https://github.com/vuhe"
        email = "zhuhe202@qq.com"
    }

    /** 协 议  */
    var licence: String = "MIT"

    /** 协 议 链 接  */
    var licenceUrl: String = "https://github.com/vuhe/AdminTemplate/blob/main/LICENSE"

    /** 组 织 链 接  */
    var termsOfServiceUrl: String = "https://github.com/vuhe"
}
