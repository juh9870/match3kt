
val kotlinVersion:String by project
plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.multiplatform") version "1.7.10" apply false
}


buildscript {
    val kotestVersion: String by project
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    dependencies {
        classpath("io.kotest:kotest-framework-multiplatform-plugin-gradle:$kotestVersion")
    }
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}