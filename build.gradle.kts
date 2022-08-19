import com.soywiz.korlibs.kotlin

plugins {
//    id("org.jetbrains.kotlin.multiplatform") version "1.7.10" apply false
}

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
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
        classpath("io.kotest:kotest-framework-multiplatform-plugin-gradle:$kotestVersion")
    }
}
//apply(plugin = "io.kotest.multiplatform")

allprojects {
    group = "com.juh9870"
    version = "1.0.0"

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
//        kotlinOptions.freeCompilerArgs += listOf("-Xno-param-assertions argument")
    }

    if (name != "scrapyard-client" && name != "scrapyard") {

        apply(plugin = "org.jetbrains.kotlin.multiplatform")

        kotlin {
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
                withJava()
                testRuns["test"].executionTask.configure {
                    useJUnitPlatform()
                }
            }
            js(IR) {
                browser {
                    commonWebpackConfig {
                        cssSupport.enabled = true
                    }
                }
            }
            val hostOs = System.getProperty("os.name")
            val isMingwX64 = hostOs.startsWith("Windows")
            val nativeTarget = when {
                hostOs == "Mac OS X" -> macosX64("native")
                hostOs == "Linux" -> linuxX64("native")
                isMingwX64 -> mingwX64("native")
                else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
            }

        }
    }
}