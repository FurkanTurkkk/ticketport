import org.gradle.kotlin.dsl.configure
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

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

    if (name.startsWith("event-")) {
        apply(plugin = "jacoco")
        configure<JacocoPluginExtension> {
            toolVersion = "0.8.12"
        }
        tasks.named<JacocoReport>("jacocoTestReport") {
            dependsOn(tasks.test)
            reports {
                xml.required.set(true)
                html.required.set(true)
            }
        }
        tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
            dependsOn(tasks.test)
            violationRules {
                rule {
                    limit {
                        counter = "LINE"
                        value = "COVEREDRATIO"
                        minimum = "1.0".toBigDecimal()
                    }
                }
            }
        }
        tasks.named("check") {
            dependsOn(tasks.named("jacocoTestCoverageVerification"))
        }
    }
}
