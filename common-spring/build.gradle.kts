@file:Suppress("SpellCheckingInspection")

dependencies {
    // 系统基础组件
    api(project(":common-api"))

    // 系统安全组件
    api("org.springframework.boot:spring-boot-starter-security")
    implementation("com.github.whvcse:easy-captcha:1.6.2")

    // 系统校验组件
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // 系统模版组件
    api("org.springframework.boot:spring-boot-starter-thymeleaf")
    api("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    api("org.thymeleaf.extras:thymeleaf-extras-java8time")

    // 系统数据库组件
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.ktorm:ktorm-core:3.4.1")
    implementation("org.ktorm:ktorm-jackson:3.4.1")
    implementation("org.ktorm:ktorm-support-mysql:3.4.1")
    implementation("mysql:mysql-connector-java")

    // 系统文档组件
    api("org.springdoc:springdoc-openapi-ui:1.6.6")
    api("org.springdoc:springdoc-openapi-kotlin:1.6.6")
}
