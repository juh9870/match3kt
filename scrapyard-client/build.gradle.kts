import com.soywiz.korge.gradle.KorgeGradlePlugin
import com.soywiz.korge.gradle.korge

apply<KorgeGradlePlugin>()

korge {
    id = "com.juh9870.match3kt"
// To enable all targets at once

    //targetAll()

// To enable targets based on properties/environment variables
    //targetDefault()

// To selectively enable targets

    targetJvm()
    targetJs()
    targetDesktop()
    //targetIos()
    targetAndroidIndirect()
    // targetAndroidDirect()

}

dependencies {
    add("commonMainImplementation", project(":match3kt"))
}