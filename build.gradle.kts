@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

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
    version = "0.3.0"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> { useJUnitPlatform() }
}

subprojects {
    dependencies {
        constraints {
            "cn.hutool:hutool".let {
                val version = "5.7.22"
                implementation("$it-core:$version")
                implementation("$it-http:$version")
                implementation("$it-system:$version")
            }
            "org.ktorm:ktorm".let {
                val version = "3.4.1"
                implementation("$it-core:$version")
                implementation("$it-jackson:$version")
                implementation("$it-support-mysql:$version")
            }
            "org.springdoc:springdoc-openapi".let {
                val version = "1.6.7"
                implementation("$it-ui:$version")
                implementation("$it-kotlin:$version")
            }
            implementation("com.github.ben-manes.caffeine:caffeine:3.0.6")
            implementation("org.jsoup:jsoup:1.14.3")
            implementation("com.github.oshi:oshi-core-java11:6.1.5")
            implementation("cn.afterturn:easypoi-base:4.4.0")
            implementation("com.github.whvcse:easy-captcha:1.6.2")
        }
    }
}
