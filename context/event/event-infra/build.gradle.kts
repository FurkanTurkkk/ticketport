dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.slf4j:slf4j-api")
    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":event-domain"))
    implementation(project(":event-application"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
