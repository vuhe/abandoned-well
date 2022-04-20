@file:Suppress("SpellCheckingInspection")

dependencies {
    // 系统基础组件
    api(project(":common-api"))
    api("org.springframework.boot:spring-boot-starter-web")

    // 系统切面组件
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // 系统安全组件
    api("org.springframework.boot:spring-boot-starter-security")
    implementation("com.github.whvcse:easy-captcha")

    // 系统校验组件
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // 系统模版组件
    api("org.springframework.boot:spring-boot-starter-thymeleaf")
    api("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    api("org.thymeleaf.extras:thymeleaf-extras-java8time")

    // 系统数据库组件
    api("org.ktorm:ktorm-core")
    implementation("org.ktorm:ktorm-jackson")
    implementation("org.ktorm:ktorm-support-mysql")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("mysql:mysql-connector-java")

    // 系统文档组件
    api("org.springdoc:springdoc-openapi-ui")
    api("org.springdoc:springdoc-openapi-kotlin")
}
