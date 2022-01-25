package top.vuhe.admin.generator.property

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * ### 代码生成配置
 * 请通过配置文件修改，不要在此处修改
 *
 * @author vuhe
 */
@ConfigurationProperties(prefix = "project.gen")
object GeneratorProperty {
    /**
     * 作者
     */
    var author: String = "vuhe"

    /**
     * 生成包路径
     */
    var packageName: String = ""

    /**
     * 自动去除表前缀，默认是false
     */
    var autoRemovePre = false

    /**
     * 表前缀(类名不会包含表前缀)
     */
    var tablePrefix: String = ""
}
