plugins {
    id("bb.android.library.convention")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.lee.bb.data.impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:data"))

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)

    // OKHttp
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}