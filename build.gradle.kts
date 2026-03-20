plugins {
	java
}

group = "com.furkan"
version = "0.0.1-SNAPSHOT"
description = "Ticket reservation and pay project with Clean Architecture"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.4"))
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
    }
}
