@file:Suppress("SpellCheckingInspection")

dependencies {
    // kotlin 扩展
    api(kotlin("reflect"))

    // hutool 工具包
    implementation("cn.hutool:hutool-core")

    // logging 工具
    compileOnly("org.aspectj:aspectjweaver")

    // jackson 工具
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")

    // 缓存工具
    implementation("com.github.ben-manes.caffeine:caffeine")

    // 网络工具
    implementation("cn.hutool:hutool-http")
    implementation("org.jsoup:jsoup")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.apache.tomcat.embed:tomcat-embed-core")

    // 系统信息获取
    implementation("cn.hutool:hutool-system")
    implementation("com.github.oshi:oshi-core-java11")

    // excel 工具
    implementation("cn.afterturn:easypoi-base")
}
