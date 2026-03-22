plugins {
    id("org.springframework.boot") version "3.2.4"
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.8")

    implementation(project(":user-domain"))
    implementation(project(":user-application"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}