description = "Lorem ipsum"
plugins {
    kotlin("multiplatform")
}

val klockVersion: String by project
val mokoVersion: String by project
dependencies {
    add("commonMainImplementation", "com.soywiz.korlibs.klock:klock:$klockVersion")
    add("commonTestImplementation", kotlin("test"))
}