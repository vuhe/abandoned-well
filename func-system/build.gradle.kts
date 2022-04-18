dependencies {
    // 系统框架组件
    api(project(":common-spring"))

    // sql 事务支持
    compileOnly("org.springframework:spring-tx")
}
