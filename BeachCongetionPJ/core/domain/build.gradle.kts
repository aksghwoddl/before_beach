plugins {
    id("bb.android.library.convention")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.lee.bb.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:data-impl"))

    // Test
    testImplementation(project(":library:test"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}