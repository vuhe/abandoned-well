package top.vuhe.admin.api.file

import com.aliyun.oss.OSS
import com.qcloud.cos.COSClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import top.vuhe.admin.api.file.operator.AliyunFileOperator
import top.vuhe.admin.api.file.operator.LocalFileOperator
import top.vuhe.admin.api.file.operator.TenFileOperator
import top.vuhe.admin.api.file.property.AliyunOssProperties
import top.vuhe.admin.api.file.property.LocalFileProperties
import top.vuhe.admin.api.file.property.TenCosProperties

/**
 * ## 文件的自动配置
 * 默认先查找阿里云的[OSS]或腾讯云的[COSClient]，
 * 找不到时使用本地文件存储；
 * 如果使用云服务，需要自己配置云服务参数
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(
    AliyunOssProperties::class, LocalFileProperties::class, TenCosProperties::class
)
class FileAutoConfiguration {
    /**
     * 阿里云文件操作，需要引入阿里云依赖
     */
    @Bean
    @Order(1)
    @ConditionalOnClass(OSS::class)
    @ConditionalOnMissingBean(FileOperatorApi::class)
    fun aliyunFileOperatorApi(prop: AliyunOssProperties): FileOperatorApi {
        log.info("系统文件使用 '阿里云' 存储")
        return AliyunFileOperator(prop)
    }

    /**
     * 腾讯云文件操作，需要引入腾讯云依赖
     */
    @Bean
    @Order(2)
    @ConditionalOnClass(COSClient::class)
    @ConditionalOnMissingBean(FileOperatorApi::class)
    fun tencentFileOperatorApi(prop: TenCosProperties): FileOperatorApi {
        log.info("系统文件使用 '腾讯云' 存储")
        return TenFileOperator(prop)
    }

    /**
     * 本地文件操作，
     * 在其它云服务依赖查找不到时使用
     */
    @Bean
    @Order(3)
    @ConditionalOnMissingBean(FileOperatorApi::class)
    @ConditionalOnMissingClass("com.aliyun.oss.OSS", "com.qcloud.cos.COSClient")
    fun localFileOperatorApi(prop: LocalFileProperties): FileOperatorApi {
        log.info("系统文件使用 '本地磁盘' 存储")
        return LocalFileOperator(prop)
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
