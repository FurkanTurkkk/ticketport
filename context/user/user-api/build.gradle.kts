dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":user-application"))
    implementation(project(":user-domain"))
}
