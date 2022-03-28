@file:Suppress("SpellCheckingInspection")

dependencies {
    // 安全组件 api
    implementation("org.springframework.security:spring-security-core")
    // 用于实现切面处理 aop
    implementation("org.springframework.boot:spring-boot-starter-aop")
    // hutool 工具包
    implementation("cn.hutool:hutool-all:5.7.20")
    // xss 工具
    implementation("org.jsoup:jsoup:1.14.3")
    // 系统信息获取
    implementation("com.github.oshi:oshi-core-java11:6.1.0")
    // excel 工具
    implementation("cn.afterturn:easypoi-spring-boot-starter:4.4.0")

    // 仅编译，如果最终不引入，直接使用内存和本地存储
    compileOnly("org.springframework.data:spring-data-redis")
}
