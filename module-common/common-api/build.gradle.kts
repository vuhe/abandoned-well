dependencies {
    // 安全组件 api
    implementation("org.springframework.security:spring-security-core")
    // 用于实现切面处理 aop
    implementation("org.springframework.boot:spring-boot-starter-aop")
    // hutool 工具包
    implementation("cn.hutool:hutool-all:5.7.19")
    // 验证码工具
    implementation("com.github.whvcse:easy-captcha:1.6.2")
    // xss 工具
    implementation("org.jsoup:jsoup:1.14.3")
    // 系统信息获取
    implementation("com.github.oshi:oshi-core-java11:5.8.6")
    // excel 工具
    implementation("cn.afterturn:easypoi-spring-boot-starter:4.4.0")

    // 仅编译，如果最终不引入，直接使用内存和本地存储
    compileOnly("com.aliyun.oss:aliyun-sdk-oss")
    compileOnly("com.qcloud:cos_api")
    compileOnly("org.springframework.data:spring-data-redis")
}
