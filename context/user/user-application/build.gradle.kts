dependencies {
    implementation("org.springframework:spring-tx")

    implementation(project(":user-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
