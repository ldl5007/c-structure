plugins {
    kotlin("jvm") version Versions.Kotlin
    kotlin("kapt") version Versions.Kotlin
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin}")

    implementation("com.squareup:kotlinpoet:1.8.0")

    implementation("com.google.auto.service:auto-service:1.0")
    kapt("com.google.auto.service:auto-service:1.0")
}