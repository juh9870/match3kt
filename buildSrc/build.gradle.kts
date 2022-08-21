plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.multiplatform") version "1.7.10" apply false
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}