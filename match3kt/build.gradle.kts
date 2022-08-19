description = "High-performance match-3 engine"

plugins {
    kotlin("multiplatform")
}

val kdsVersion: String by project
dependencies {
    add("commonMainImplementation", "com.soywiz.korlibs.kds:kds:$kdsVersion")
    add("commonMainImplementation", project(":scrapyard-utils"))
    add("commonTestImplementation", kotlin("test"))
}
