dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.security:spring-security-crypto")
    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":user-domain"))
    implementation(project(":user-application"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
