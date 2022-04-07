package top.vuhe.admin.spring.config

import org.ktorm.database.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * ### ktorm dao 层 配置
 *
 * @author vuhe
 */
@Configuration(proxyBeanMethods = false)
class KtormConfiguration {
    /**
     * Register the [Database] instance as a Spring bean.
     */
    @Bean
    fun database(dataSource: DataSource): Database {
        database =  Database.connectWithSpringSupport(dataSource)
        return database
    }

    companion object {
        internal lateinit var database: Database
    }
}
