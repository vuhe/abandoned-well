@file:Suppress("SpellCheckingInspection")

dependencies {
    // kotlin 扩展
    implementation(kotlin("reflect"))

    // logging 工具
    compileOnly("org.aspectj:aspectjweaver")

    // jackson 工具
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
    compileOnly("com.fasterxml.jackson.core:jackson-core")
    compileOnly("com.fasterxml.jackson.core:jackson-databind")

    // 缓存工具
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.0")

    // 网络工具
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("eu.bitwalker:UserAgentUtils:1.21")
    compileOnly("org.springframework:spring-web")
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    // 系统信息获取
    implementation("com.github.oshi:oshi-core-java11:6.1.6")

    // excel 工具
    implementation("cn.afterturn:easypoi-base:4.4.0")
}
