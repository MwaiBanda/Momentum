plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.7.21"
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.mwaibanda.momentum.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 18
        versionName = "1.1.7"

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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
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
    }
    namespace = "com.mwaibanda.momentum.android"
}

dependencies {
    implementation(project(":MomentumSDK"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.6.0")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.2.1")
    // Animations
    implementation("androidx.compose.animation:animation:1.2.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    //Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("app.cash.turbine:turbine:0.8.0")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.2")
    // Icons exts
    implementation("androidx.compose.material:material-icons-extended:1.2.1")
    // Status Bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.26.5-rc")
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
    implementation("dev.gitlive:firebase-auth:1.6.2")
    implementation("dev.gitlive:firebase-firestore:1.6.2")
    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation("com.google.android.play:review:2.0.1")
    // For Kotlin users also add the Kotlin extensions library for Play In-App Review:
    implementation("com.google.android.play:review-ktx:2.0.0")
    // Coil Image loading
    implementation("io.coil-kt:coil-compose:2.2.1")
    // Exoplayer
//    implementation("androidx.media3:media3-exoplayer:1.0.0-beta01")
//    implementation("androidx.media3:media3-common:1.0.0-beta01")
//    implementation("androidx.media3:media3-ui:1.0.0-beta01")
//    implementation("androidx.media3:media3-cast:1.0.0-beta01")

    implementation("com.google.android.exoplayer:exoplayer:2.18.0")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.18.0")
    implementation("com.google.android.exoplayer:extension-cast:2.18.0")

    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // Auth
    implementation("io.github.mwaibanda:authentication:1.0.0")

    //
    implementation("org.slf4j:slf4j-simple:1.6.1")

}














































































































































































































































tasks.withType<Test> {
    useJUnitPlatform()
}