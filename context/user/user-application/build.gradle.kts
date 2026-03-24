dependencies {
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-context")

    implementation(project(":user-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
