plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"
    signing
}

group = "io.github.purofle"
version = "1.0"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    api("org.telegram:telegrambots-longpolling:9.0.0")
    api("org.telegram:telegrambots-client:9.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")

    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

kotlin {
    jvmToolchain(17)
}