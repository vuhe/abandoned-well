dependencies {
    implementation(project(":func-schedule"))
    implementation(project(":func-system"))
    implementation(project(":func-well"))

    implementation("mysql:mysql-connector-java")
    implementation("org.ktorm:ktorm-support-mysql")
}
