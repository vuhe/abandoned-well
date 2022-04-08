dependencies {
    // 系统框架组件
    api(project(":common-spring"))

    // 校验注解
    compileOnly("jakarta.validation:jakarta.validation-api")
    compileOnly("org.hibernate.validator:hibernate-validator")
}
