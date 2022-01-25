package top.vuhe.admin.api.file.property

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 文件上传阿里云配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("project.upload.aliyun")
object AliyunOssProperties {
    /**
     * 默认北京，内网，[其它地址](https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.17.467f45dcjB4WQQ#concept-zt4-cvy-5db)
     */
    var endPoint = "http://oss-cn-beijing.aliyuncs.com"

    /**
     * 秘钥id
     */
    var accessKeyId: String = ""

    /**
     * 秘钥secret
     */
    var accessKeySecret: String = ""

    /**
     * 应用桶名称
     */
    var bucketName: String = ""
}
