package top.vuhe.admin.spring.property

import io.swagger.v3.oas.models.info.Contact
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * ### 接口文档配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("springdoc.swagger")
object SwaggerProperty {
    /** 系 统 标 题  */
    var title: String? = null

    /** 描 述 信 息  */
    var describe: String? = null

    /** 版 本 信 息  */
    var version: String? = null

    /** 扫 描 路 径  */
    var scanPackage: String? = null

    /** 扩 展 信 息  */
    var contact: Contact? = null

    /** 协 议  */
    var licence: String? = null

    /** 协 议 链 接  */
    var licenceUrl: String? = null

    /** 组 织 链 接  */
    var termsOfServiceUrl: String? = null
}
