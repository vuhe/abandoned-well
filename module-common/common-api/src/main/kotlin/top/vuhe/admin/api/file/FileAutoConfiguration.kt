package top.vuhe.admin.api.file

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ## 文件的自动配置
 * 使用本地文件存储；
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class FileAutoConfiguration {
    @Bean
    fun localFileOperatorApi(): FileOperatorApi = LocalFileOperator
}
