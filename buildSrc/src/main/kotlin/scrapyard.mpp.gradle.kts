plugins {
    kotlin("multiplatform")
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
    google()
}

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
    @Suppress("UNUSED_VARIABLE") val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
}

val kotestVersion: String by project
dependencies {
    add("commonTestImplementation", kotlin("test"))
    add("commonTestImplementation", "io.kotest:kotest-framework-engine:$kotestVersion")
    add("commonTestImplementation", "io.kotest:kotest-property:$kotestVersion")
}