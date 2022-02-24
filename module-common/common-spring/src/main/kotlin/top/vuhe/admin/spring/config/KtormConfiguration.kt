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
@Configuration
class KtormConfiguration {
    /**
     * Register the [Database] instance as a Spring bean.
     */
    @Bean
    fun database(dataSource: DataSource): Database {
        return Database.connectWithSpringSupport(dataSource)
    }
}
