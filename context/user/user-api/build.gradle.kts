dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":user-application"))
    implementation(project(":user-domain"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
