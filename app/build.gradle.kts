
plugins {
    java
    kotlin("jvm") version Versions.Kotlin
    kotlin("kapt") version Versions.Kotlin
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":processor"))
    kapt(project(":processor"))
}