plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("org.jetbrains.kotlin.plugin.compose") version libs.versions.kotlin
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    compileSdk = 35
    defaultConfig {
        applicationId = "com.mwaibanda.momentum.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 39
        versionName = "1.3.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    // Set both the Java and Kotlin compilers to target Java 8.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility  = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources  = true
        unitTests.isReturnDefaultValues = true
        execution = "ANDROID_TEST_ORCHESTRATOR"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    namespace = "com.mwaibanda.momentum.android"
}

dependencies {
    implementation(project(":MomentumSDK"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.ui)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.material)
    // Integration with activities
    implementation(libs.activity.compose)
    // Compose Material Design
    implementation(libs.androidx.material)
    // Animations
    implementation(libs.androidx.animation)
    // Tooling support (Previews, etc.)
    implementation(libs.androidx.ui.tooling)
    // Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.messaging)
    // UI Tests
    androidTestImplementation(libs.androidx.ui.test.junit4)
    //Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.turbine)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Icons exts
    implementation(libs.androidx.material.icons.extended)
    // Status Bar
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.insets)
    //Placeholder
    implementation(libs.accompanist.placeholder.material)
    // Stripe Android SDK
    implementation(libs.stripe.android)
    //Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    // Koin Jetpack Compose
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)
    //Lottie
    implementation(libs.lottie.compose)
    // Bottom Sheet
    implementation(libs.accompanist.navigation.material)
    // Firebase Auth Kotlin SDK
    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation(libs.review)
    // For Kotlin users also add the Kotlin extensions library for Play In-App Review:
    implementation(libs.review.ktx)
    // Coil Image loading
    implementation(libs.coil.compose)
    // Exoplayer
    implementation(libs.exoplayer)
    implementation(libs.exoplayer.ui)
    implementation(libs.extension.cast)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // Auth
    implementation(libs.authentication)

    // Logging
    implementation(libs.slf4j.simple)

    // Calendar
    implementation( libs.composecalendar)
    // separate artifact with utilities for working with kotlinx-datetime
    implementation(libs.composecalendar.kotlinx.datetime)

}














































































































































































































































tasks.withType<Test> {
    useJUnitPlatform()
}