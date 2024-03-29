package top.vuhe.config

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.EnableTransactionManagement
import top.vuhe.logging.LoggingFactory
import top.vuhe.logging.LoggingHandler
import top.vuhe.monitor.Monitor
import javax.sql.DataSource

/**
 * ### 核心配置
 *
 * @author vuhe
 */
@EnableTransactionManagement
@Configuration(proxyBeanMethods = false)
class SystemConfig {

    /** 日志记录 */
    @Bean
    fun loggingAspect(factory: LoggingFactory) = LoggingHandler(factory)

    /** 注册 database 作为 bean */
    @Bean
    fun database(dataSource: DataSource): Database {
        return Database.connectWithSpringSupport(dataSource)
    }

    /** 硬件信息刷新任务 */
    @Scheduled(fixedDelay = 60 * 1000)
    fun hardwareRefresher() = Monitor.refresh()

    /** 加载 objectMapperBuilder */
    @Autowired
    fun objMapper(mapper: Jackson2ObjectMapperBuilder) {
        builder = mapper
    }

    companion object {
        /** 此变量应该在 spring 正确启动后初始化完成 */
        internal lateinit var builder: Jackson2ObjectMapperBuilder
    }
}
