package top.vuhe.admin.api.file

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 文件上传本地配置类
 *
 * @author vuhe
 */
@ConfigurationProperties("project.upload.local")
class LocalFileProperties {
    /**
     * windows 系统文件上传路径
     */
    var windowsPath: String = "D:\\tempFilePath"

    /**
     * linux 系统文件上传路径
     */
    var linuxPath: String = "/tmp/tempFilePath"

    /**
     * mac 系统文件上传路径
     */
    var macPath: String = "./tmp/tempFilePath"
}
