import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("org.flywaydb.flyway") version "9.22.3"
}

tasks.withType<BootJar>().configureEach {
    archiveFileName.set("ticketport.jar")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/ticketport"
    user = "ticketport"
    password = "ticketport"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.4"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.flywaydb:flyway-core")

    implementation(project(":security"))

    implementation(project(":user-application"))
    implementation(project(":user-infra"))
    implementation(project(":user-api"))

    implementation(project(":event-application"))
    implementation(project(":event-infra"))
    implementation(project(":event-api"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
