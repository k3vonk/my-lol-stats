import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("kapt") version "1.9.22"

    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation(libs.guava)

    implementation("org.springframework.boot:spring-boot-starter-web") // communicate with Riot API
    implementation("org.springframework.boot:spring-boot-starter-webflux") // service's public API
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // helps annotate configuration properties
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation(kotlin("reflect"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
        }
    }
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
