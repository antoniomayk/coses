import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    id("org.flywaydb.flyway") version "10.7.2"
    id("org.springframework.boot") version "3.2.2"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("idea")
}

group = "io.antoniomayk"
version = "0.0.1-SNAPSHOT"

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
    implementation("org.flywaydb:flyway-core:10.7.2")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.7.2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(
            group = "org.mockito",
            module = "mockito-core"
        )
        exclude(
            group = "org.junit.vintage",
            module = "junit-vintage-engine"
        )
    }
    testImplementation("org.testcontainers:junit-jupiter:1.19.5")
    testImplementation("org.testcontainers:postgresql:1.19.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
