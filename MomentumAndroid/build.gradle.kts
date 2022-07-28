plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    id("com.google.gms.google-services")

}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.mwaibanda.momentum.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
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
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":MomentumSDK"))
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Integration with activities
    implementation( "androidx.activity:activity-compose:1.5.1")
    // Compose Material Design
    implementation( "androidx.compose.material:material:1.2.0")
    // Animations
    implementation("androidx.compose.animation:animation:1.2.0")
    // Tooling support (Previews, etc.)
    implementation( "androidx.compose.ui:ui-tooling:1.2.0")
    // Integration with ViewModels
    implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("com.google.firebase:firebase-auth:21.0.6")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0")
    // Navigation
    implementation( "androidx.navigation:navigation-compose:2.5.1")
    // Live Data
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.0-alpha02")
    // Icons exts
    implementation( "androidx.compose.material:material-icons-extended:1.2.0")
    // Status Bar
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.24.13-rc")
    implementation ("com.google.accompanist:accompanist-insets:0.24.13-rc")

    // Stripe Android SDK
    implementation ("com.stripe:stripe-android:19.0.0")
    //Koin
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-core:3.2.0")
    // Koin Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0")
    //Lottie
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    // Bottom Sheet
    implementation("com.google.accompanist:accompanist-navigation-material:0.24.13-rc")


}