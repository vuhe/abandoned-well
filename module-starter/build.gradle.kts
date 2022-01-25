dependencies {
    implementation(project(":func-generator"))
    implementation(project(":func-schedule"))
    implementation(project(":func-system"))

    implementation("mysql:mysql-connector-java")
    implementation("org.ktorm:ktorm-support-mysql")
}
