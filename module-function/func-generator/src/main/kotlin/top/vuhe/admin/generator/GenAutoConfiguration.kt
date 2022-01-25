package top.vuhe.admin.generator

import org.apache.velocity.app.Velocity
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import top.vuhe.admin.generator.property.GeneratorProperty
import javax.annotation.PostConstruct

/**
 * 代码自动生成的自动配置
 *
 * @author vuhe
 */
@Configuration
@EnableConfigurationProperties(GeneratorProperty::class)
class GenAutoConfiguration {

    @PostConstruct
    fun init() {
        // 由于本系统使用 velocity 时是单例模式，
        // 因此在此初始化一次即可
        Velocity.setProperty(
            "resource.loader.file.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
        )
        Velocity.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8")
        Velocity.init()
    }
}
