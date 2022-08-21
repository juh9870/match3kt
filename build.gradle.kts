//plugins {
//    id("org.jetbrains.kotlin.multiplatform") version "1.7.10" apply false
//}

buildscript {
    val korgePluginVersion: String by project
    val kotestVersion: String by project

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    dependencies {
//        classpath("org.jetbrains.kotlin.multiplatform")
//        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
        classpath("io.kotest:kotest-framework-multiplatform-plugin-gradle:$kotestVersion")
    }
}
//apply(plugin = "io.kotest.multiplatform")

allprojects {
    group = "com.juh9870"
    version = "1.0.0"
}