@file:Suppress("SpellCheckingInspection")

dependencies {
    // kotlin 扩展
    api(kotlin("reflect"))

    // hutool 工具包
    implementation("cn.hutool:hutool-core:5.7.22")
    implementation("cn.hutool:hutool-extra:5.7.22")

    // jackson 工具
    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // logging 工具
    compileOnly("org.aspectj:aspectjweaver")
    compileOnly("org.springframework:spring-context")

    // 缓存工具
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.6")

    // 网络工具
    implementation("cn.hutool:hutool-http:5.7.22")
    implementation("org.jsoup:jsoup:1.14.3")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.apache.tomcat.embed:tomcat-embed-core")

    // 系统信息获取
    implementation("cn.hutool:hutool-system:5.7.22")
    implementation("com.github.oshi:oshi-core-java11:6.1.5")

    // excel 工具
    implementation("cn.afterturn:easypoi-base:4.4.0")
}
