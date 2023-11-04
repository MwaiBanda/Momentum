plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.9.10"
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.mwaibanda.momentum.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 20
        versionName = "1.1.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildFeatures {
        compose = true
    }

    // Set both the Java and Kotlin compilers to target Java 8.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.android.material:material:1.10.0")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.8.0")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.5.4")
    // Animations
    implementation("androidx.compose.animation:animation:1.5.4")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    //Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("app.cash.turbine:turbine:0.8.0")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")
    // Icons exts
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    // Status Bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.3-beta")
    implementation("com.google.accompanist:accompanist-insets:0.26.5-rc")
    //Placeholder
    implementation("com.google.accompanist:accompanist-placeholder-material:0.26.5-rc")
    // Stripe Android SDK
    implementation("com.stripe:stripe-android:19.0.0")
    //Koin
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-core:3.2.0")
    // Koin Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0")
    //Lottie
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    // Bottom Sheet
    implementation("com.google.accompanist:accompanist-navigation-material:0.25.0")
    // Firebase Auth Kotlin SDK
    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation("com.google.android.play:review:2.0.1")
    // For Kotlin users also add the Kotlin extensions library for Play In-App Review:
    implementation("com.google.android.play:review-ktx:2.0.1")
    // Coil Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Exoplayer
//    implementation("androidx.media3:media3-exoplayer:1.0.0-beta01")
//    implementation("androidx.media3:media3-common:1.0.0-beta01")
//    implementation("androidx.media3:media3-ui:1.0.0-beta01")
//    implementation("androidx.media3:media3-cast:1.0.0-beta01")

    implementation("com.google.android.exoplayer:exoplayer:2.18.0")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.18.0")
    implementation("com.google.android.exoplayer:extension-cast:2.18.0")

    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Auth
    implementation("io.github.mwaibanda:authentication:1.0.5")

    // Logging
    implementation("org.slf4j:slf4j-simple:2.0.7")

    // Calendar
    implementation( "io.github.boguszpawlowski.composecalendar:composecalendar:1.1.1")
    // separate artifact with utilities for working with kotlinx-datetime
    implementation("io.github.boguszpawlowski.composecalendar:kotlinx-datetime:1.1.1")

}














































































































































































































































tasks.withType<Test> {
    useJUnitPlatform()
}