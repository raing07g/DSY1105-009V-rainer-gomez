plugins {
    kotlin("jvm") version "2.4.10"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    application


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

kotlin {
    jvmToolchain(26)
}

tasks.test {
    useJUnitPlatform()
}
