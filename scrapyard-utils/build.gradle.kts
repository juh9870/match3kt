description = "Serialization module"

plugins {
    id("scrapyard.mpp")
}

val kdsVersion: String by project
val kmemVersion: String by project
dependencies {
    add("commonMainImplementation", "com.soywiz.korlibs.kds:kds:$kdsVersion")
    add("commonMainImplementation", "com.soywiz.korlibs.kmem:kmem:$kmemVersion")
}
