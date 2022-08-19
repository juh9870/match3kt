description = "Serialization module"

plugins {
    kotlin("multiplatform")
    id("io.kotest.multiplatform")
}

val kdsVersion: String by project
val kotestVersion: String by project
val kmemVersion: String by project
dependencies {
    add("commonTestImplementation", kotlin("test"))
    add("commonMainImplementation", "com.soywiz.korlibs.kds:kds:$kdsVersion")
    add("commonMainImplementation", "com.soywiz.korlibs.kmem:kmem:$kmemVersion")

    add("commonTestImplementation", "io.kotest:kotest-framework-engine:$kotestVersion")
    add("commonTestImplementation", "io.kotest:kotest-property:$kotestVersion")
}
