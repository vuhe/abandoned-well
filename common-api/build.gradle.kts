@file:Suppress("SpellCheckingInspection")

dependencies {
    // kotlin 扩展
    implementation(kotlin("reflect"))

    // 系统基础组件
    api("org.springframework.boot:spring-boot-starter-web")

    // 系统切面组件
    api("org.springframework.boot:spring-boot-starter-aop")

    // jackson 工具
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // 缓存工具
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.0")

    // 网络工具
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("eu.bitwalker:UserAgentUtils:1.21")

    // 系统信息获取
    implementation("com.github.oshi:oshi-core-java11:6.1.6")

    // excel 工具
    implementation("cn.afterturn:easypoi-base:4.4.0")
}
