package top.vuhe.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WellManagementApplication

fun main(args: Array<String>) {
    runApplication<WellManagementApplication>(profilesActive(args))
}

/** 参数仅用于选择内部配置使用，其他命令行参数将不起作用 */
private fun profilesActive(args: Array<String>): String {
    val action = when (args.getOrNull(0)) {
        "prod" -> "prod"
        // 未找到其他配置的情况下，直接使用 dev 配置
        else -> "dev"
    }
    return "--spring.profiles.active=$action"
}
