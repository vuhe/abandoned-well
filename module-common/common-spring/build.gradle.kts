dependencies {
    // 系统基础组件
    api(project(":common-api"))

    // 系统缓存组件
    api("org.springframework.boot:spring-boot-starter-cache")
    compileOnly("org.springframework.data:spring-data-redis")

    // 系统安全组件
    api("org.springframework.boot:spring-boot-starter-security")

    // 系统校验组件
    api("org.springframework.boot:spring-boot-starter-validation")

    // 系统模版组件
    api("org.springframework.boot:spring-boot-starter-thymeleaf")
    api("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    api("org.thymeleaf.extras:thymeleaf-extras-java8time")

    // 系统数据库组件
    api("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.ktorm:ktorm-core")
    implementation("org.ktorm:ktorm-jackson")

    // 系统文档组件
    api("org.springdoc:springdoc-openapi-ui")
    api("org.springdoc:springdoc-openapi-kotlin")
}
