@file:Suppress("UnstableApiUsage")

import dev.monosoul.jooq.RecommendedVersions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    val kotlinVersion = "1.9.22"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("dev.monosoul.jooq-docker") version "6.0.13"

    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"

    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-web") // communicate with Riot API
    implementation("org.springframework.boot:spring-boot-starter-webflux") // service's public API
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // database
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.postgresql:postgresql")
    jooqCodegen("org.postgresql:postgresql")

    // database migrations
    project.extra["jooq.version"] = RecommendedVersions.JOOQ_VERSION
    project.extra["flyway.version"] = RecommendedVersions.FLYWAY_VERSION
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")

    // helps annotate configuration properties
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation(kotlin("reflect"))

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")

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
