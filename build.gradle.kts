buildscript {
    @Suppress("UNUSED_VARIABLE") val korgePluginVersion: String by project
    val kotestVersion: String by project

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    dependencies {
//        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
        classpath("io.kotest:kotest-framework-multiplatform-plugin-gradle:$kotestVersion")
    }
}

allprojects {
    group = "com.juh9870"
    version = "1.0.0"
}