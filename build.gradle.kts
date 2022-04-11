@file:Suppress("SpellCheckingInspection")

plugins {
    id("org.springframework.boot") version "2.6.6"
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
}

dependencies {
    implementation(project(":func-system"))
    implementation(project(":func-well"))
}

springBoot { mainClass.set("top.vuhe.admin.WellManagementAppKt") }

allprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "top.vuhe"
    version = "0.2.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("reflect"))
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
        implementation("org.springframework.boot:spring-boot-starter-web")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> { useJUnitPlatform() }
}
