@file:Suppress("SpellCheckingInspection")

dependencies {
    // 安全组件 api
    implementation("org.springframework.security:spring-security-core")

    // 切面处理 aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // hutool 工具包
    implementation("cn.hutool:hutool-core:5.7.22")

    // 缓存工具
    implementation("cn.hutool:hutool-cache:5.7.22")
    compileOnly("org.springframework.data:spring-data-redis")

    // 网络工具
    implementation("cn.hutool:hutool-http:5.7.22")
    implementation("org.jsoup:jsoup:1.14.3")

    // 系统信息获取
    implementation("cn.hutool:hutool-extra:5.7.22")
    implementation("cn.hutool:hutool-system:5.7.22")
    implementation("com.github.oshi:oshi-core-java11:6.1.5")

    // excel 工具
    implementation("cn.afterturn:easypoi-base:4.4.0")
}
