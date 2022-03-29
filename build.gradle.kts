plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

dependencies {
    implementation(project(":func-system"))
    implementation(project(":func-well"))

    implementation("mysql:mysql-connector-java")
    implementation("org.ktorm:ktorm-support-mysql")
}

springBoot {
    mainClass.set("top.vuhe.admin.WellManagementAppKt")
}

allprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")

    group = "top.vuhe"
    version = "0.1.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-starter-web")

        // 注解处理器
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    // 子项目依赖管理
    dependencyManagement {
        dependencies {
            // 数据库连接工具
            dependencySet("org.ktorm:3.4.1") {
                entry("ktorm-core")
                entry("ktorm-jackson")
                entry("ktorm-support-mysql")
            }
            // swagger 文档
            dependencySet("org.springdoc:1.6.4") {
                entry("springdoc-openapi-ui")
                entry("springdoc-openapi-kotlin")
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    // 子项目跳过 bootJar
    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
}
