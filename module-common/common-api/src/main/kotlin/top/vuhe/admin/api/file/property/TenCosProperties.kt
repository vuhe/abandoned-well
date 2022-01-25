package top.vuhe.admin.api.file.property

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 文件上传腾讯云配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("project.upload.tencent")
object TenCosProperties {
    /**
     * secretId
     */
    val secretId: String = ""

    /**
     * secretKey
     */
    val secretKey: String = ""

    /**
     * 地域id（默认北京）
     */
    val regionId = "ap-beijing"

    /**
     * 应用桶名称
     */
    var bucketName: String = ""
}
