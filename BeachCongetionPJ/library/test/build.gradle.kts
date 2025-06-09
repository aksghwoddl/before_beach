plugins {
    id("bb.android.library.convention")
}

android {
    namespace = "com.lee.bb.test"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(libs.bundles.unit.test)
}