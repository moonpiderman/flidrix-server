import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.noarg") version "1.6.21"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("kotlin-spring")
        plugin("io.spring.dependency-management")
    }

    group = "flidrix.root"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.flywaydb:flyway-core")
        implementation("org.flywaydb:flyway-mysql")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")
        runtimeOnly("io.r2dbc:r2dbc-h2")

        testImplementation("io.mockk:mockk:1.12.4")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
        testImplementation("io.kotest:kotest-assertions-core:5.3.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        systemProperty("spring.profiles.active", "test")
        useJUnitPlatform()
    }
}

project(":api") {
    tasks.bootJar {
        archiveFileName.set("flidrix-api.jar")
    }
}

project(":core") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false
