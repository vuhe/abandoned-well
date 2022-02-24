package top.vuhe.admin.api.file

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ## 文件的自动配置
 * 使用本地文件存储；
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LocalFileProperties::class)
class FileAutoConfiguration {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 本地文件操作，
     * 在其它云服务依赖查找不到时使用
     */
    @Bean
    fun localFileOperatorApi(prop: LocalFileProperties): FileOperatorApi {
        log.info("[local file] System File will be saved in local disk !")
        return LocalFileOperator(prop)
    }
}
