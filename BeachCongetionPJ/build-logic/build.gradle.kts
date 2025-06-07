plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.build.gradle)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "bb.android.library.convention"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}